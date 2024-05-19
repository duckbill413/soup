# 1. 개발 환경

### 🖥️ Frontend

- TypeScript
- Node 20.11.1
- Next.js 14.1.3
- React 18
- React-dom 18
- VS Code 1.85.1
- ESLint 8

### 📋 Backend

- Java 17
- Spring Boot 3.x
- Spring Security 6.x
- Spring Data JPA
- JWT
- lombok / @Log4j2
- Gradle
- WebSocket
- WebRTC - Openvidu

### 📚 DB

- PostgreSQL
- MongoDB
- Redis

### 🌏 Infra

- AWS EC2 Ubuntu
- Jenkins
- Docker
- Docker compose
- Nginx
- Jenkins
- Spring Actuator

### 📢 Communication

- 형상 관리 - `Gitlab`
- 이슈 및 스크럼 관리 - `Jira`
- 의사소통, 협업 - `Notion`, `Mattermost`
- 디자인 - `Figma`

# 2. EC2 서버 설정

## 1. Jenkins

### 1. docker가 설치된 jenkins 이미지 만들기

`docker-install.sh` 파일을 먼저 생성 후 아래의 코드 입력합니다.

```bash
#!/bin/sh
apt-get update && \
apt-get -y install apt-transport-https \
     ca-certificates \
     curl \
     gnupg2 \
     zip \
     unzip \
     software-properties-common && \
curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg > /tmp/dkey; apt-key add /tmp/dkey && \
add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") \
   $(lsb_release -cs) \
   stable" && \
apt-get update && \
apt-get -y install docker-ce
```

`dockerfile` 생성해줍니다

```docker
FROM jenkins/jenkins:lts

#root 계정으로 변경(for docker install)
USER root

COPY docker-install.sh /docker-install.sh
RUN chmod +x /docker-install.sh
RUN /docker-install.sh

RUN usermod -aG docker jenkins
USER jenkins
```

위와 같이 `docker-install.sh`와 `dockerfile`을 만든후 아래의 명령어로 image 생성합니다.

```bash
docker build -t jenkins-docker .
```

### 2. Front Pipeline Script

본 프로젝트는 `front`, `back` 2개의 브랜치로 나누어 관리하기 때문에 2개의 파이프라인에 각각의 script를 작성해야 합니다.

우선 Front부분 파이프라인 script 입니다.

`Front pipeline Script`

```bash
pipeline {
    agent any
    tools {nodejs "nodejs"}

    environment {
        repository = "awetumnn/a201-front" // Docker 이미지의 저장소 경로
        dockerImage = '' // Docker 이미지 변수 초기화
        
        registryCredential = 'dockerhub-access'
        
        releaseServerAccount = 'ubuntu'
        releaseServerUri = 'k10a201.p.ssafy.io'
        releasePort = '80'
        
    }

    stages {
        stage('Git Clone') {
            steps {
                git branch: 'frontend', 
                credentialsId:'gitlab-user-access',
                url: 'https://lab.ssafy.com/s10-final/S10P31A201'
            }
        }
        stage('Node Build') {
            steps {
                dir ('../soup-frontend/front') {
                    sh 'npm install -g pnpm'
                    sh 'pnpm install --no-frozen-lockfile'
                    sh 'pnpm install next'
                    sh "GENERATE_SOURCEMAP=false \
                        NEXT_PUBLIC_LIVEBLOCKS_PUBLIC_KEY=${env.NEXT_PUBLIC_LIVEBLOCKS_PUBLIC_KEY} \
                        NEXT_PUBLIC_SERVER_HOST=${env.NEXT_PUBLIC_SERVER_HOST} \
                        NEXT_PUBLIC_BACKEND_BASE_URL=${env.NEXT_PUBLIC_BACKEND_BASE_URL} \
                        NEXT_PUBLIC_SERVER_SOCKET=${env.NEXT_PUBLIC_SERVER_SOCKET} \
                        pnpm run build"
                }
            }
        }
        stage('Image Build & DockerHub Push') {
            steps {
                dir('../soup-frontend/front') {
                    script {
                        docker.withRegistry('', registryCredential) {
                            sh "docker buildx create --use --name mybuilder"
                            sh "docker buildx build --platform linux/amd64 -t ${repository}:${BUILD_NUMBER} --push \
                                --build-arg NEXT_PUBLIC_LIVEBLOCKS_PUBLIC_KEY=${env.NEXT_PUBLIC_LIVEBLOCKS_PUBLIC_KEY} \
                                --build-arg NEXT_PUBLIC_SERVER_HOST=${env.NEXT_PUBLIC_SERVER_HOST} \
                                --build-arg NEXT_PUBLIC_BACKEND_BASE_URL=${env.NEXT_PUBLIC_BACKEND_BASE_URL} \
                                --build-arg NEXT_PUBLIC_SERVER_SOCKET=${env.NEXT_PUBLIC_SERVER_SOCKET} ."
                            sh "docker buildx build --platform linux/amd64 -t ${repository}:latest --push \
                                --build-arg NEXT_PUBLIC_LIVEBLOCKS_PUBLIC_KEY=${env.NEXT_PUBLIC_LIVEBLOCKS_PUBLIC_KEY} \
                                --build-arg NEXT_PUBLIC_SERVER_HOST=${env.NEXT_PUBLIC_SERVER_HOST} \
                                --build-arg NEXT_PUBLIC_BACKEND_BASE_URL=${env.NEXT_PUBLIC_BACKEND_BASE_URL} \
                                --build-arg NEXT_PUBLIC_SERVER_SOCKET=${env.NEXT_PUBLIC_SERVER_SOCKET} ."
                        }
                    }
                }
            }
        }
        stage('Before Service Stop') {
            steps {
                sshagent(credentials: ['ubuntu-a201']) {
                sh '''
                CONTAINER_IDS=$(ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri "docker ps -aq --filter ancestor=${repository}:latest")
                if [ ! -z "$CONTAINER_IDS" ]; then
                    echo "$CONTAINER_IDS" | xargs -I {} ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri "docker stop {}"
                    echo "$CONTAINER_IDS" | xargs -I {} ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri "docker rm -f {}"
                fi
                IMAGE_EXISTS=$(ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri "docker images -q ${repository}:latest")
                if [ ! -z "$IMAGE_EXISTS" ]; then
                    ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri "docker rmi ${repository}:latest"
                fi
                '''
                }
            }
        }
        stage('DockerHub Pull') {
            steps {
                sshagent(credentials: ['ubuntu-a201']) {
                    sh "ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri 'docker pull ${repository}:latest'"
                }
            }
        }
        stage('Volume Initialization') {
            steps {
                sshagent(credentials: ['ubuntu-a201']) {
                    script {
                        // 컨테이너 존재 여부 확인
                        def isContainerRunning = sh(
                            script: "ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri 'docker ps -q -f name=ubuntu-nginx-1'",
                            returnStdout: true
                        ).trim()

                        // 조건에 따라 컨테이너 및 볼륨 관리
                        if (isContainerRunning) {
                            echo 'Container exists, proceeding with stop, remove, and volume removal.'
                            sh '''
                                ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri "docker stop ubuntu-nginx-1"
                                ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri "docker rm ubuntu-nginx-1"
                                ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri "docker volume rm ubuntu_nextjs-static ubuntu_public-files"
                            '''
                        } else {
                            echo 'Container does not exist, skipping stop, remove, and volume removal.'
                        }
                    }
                }
            }
        }
        stage('Service Start') {
            steps {
                sshagent(credentials: ['ubuntu-a201']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri "sudo docker compose up -d soup-front "
                        ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri "sudo docker compose up -d nginx "
                    '''
                }
            }
        }
        stage('Service Check') {
            steps {
                sshagent(credentials: ['ubuntu-a201']) {
                    sh '''
                        #!/bin/bash
                        
                        for retry_count in $(seq 20)
                        do
                          if curl -s "https://so-up.store" > /dev/null
                          then
                              curl -d '{
                                          "text": "[FRONTEND] Deployment Success",
                                          "attachments": [
                                            {
                                              "color": "good",
                                              "text": "FRONTEND-DEV : The deployment was successful. The application is running smoothly."
                                            }
                                          ]
                                        }' -H "Content-Type: application/json" -X POST https://meeting.ssafy.com/hooks/hraztumtdbbzd8dft6q8mczywy
                              break
                          fi
                        
                          if [ $retry_count -eq 20 ]
                          then
                            curl -d '{
                                        "text": "[FRONTEND] Deployment Failure",
                                        "attachments": [
                                            {
                                              "color": "danger",
                                              "text": "FRONTEND-DEV : The deployment failed. Please check the deployment logs for more information."
                                            }
                                        ]
                                    }' -H "Content-Type: application/json" -X POST https://meeting.ssafy.com/hooks/hraztumtdbbzd8dft6q8mczywy
                            exit 1
                          fi
                        
                          echo "The server is not alive yet. Retry health check in 5 seconds..."
                          sleep 5
                        done
                    '''
                }
            }
        }
    }
}
```

`Dockerfile` 작성

```docker
# 기본 이미지 및 환경변수 설정
FROM node:20.11.1 AS builder

ARG NEXT_PUBLIC_LIVEBLOCKS_PUBLIC_KEY
ARG NEXT_PUBLIC_SERVER_HOST
ARG NEXT_PUBLIC_BACKEND_BASE_URL
ARG NEXT_PUBLIC_SERVER_SOCKET

ENV NEXT_PUBLIC_LIVEBLOCKS_PUBLIC_KEY=$NEXT_PUBLIC_LIVEBLOCKS_PUBLIC_KEY \
    NEXT_PUBLIC_SERVER_HOST=$NEXT_PUBLIC_SERVER_HOST \
    NEXT_PUBLIC_BACKEND_BASE_URL=$NEXT_PUBLIC_BACKEND_BASE_URL \
    NEXT_PUBLIC_SERVER_SOCKET=$NEXT_PUBLIC_SERVER_SOCKET

WORKDIR /app

# package.json과 기타 필요한 파일들만 먼저 복사
COPY package*.json pnpm-lock.yaml ./

# pnpm 설치 및 의존성 설치
RUN npm install -g pnpm
RUN pnpm install

# 전체 코드 복사 전 node_modules 제거
RUN rm -rf node_modules

# 전체 소스 복사
COPY . .

# 프로젝트 빌드
RUN pnpm run build

# 런타임 이미지 준비
FROM node:20.11.1
WORKDIR /app

ENV NODE_ENV=production

# 빌드 결과물 복사 
COPY --from=builder /app/.next/standalone ./
COPY --from=builder /app/.next/static ./.next/static
COPY --from=builder /app/public ./public 

# 포트 노출 및 서버 실행
EXPOSE 3000
CMD ["node", "server.js"]

```

### 3. Back Pipeline Script

다음은 Back 부분 파이프라인 script 입니다.

`Back pipeline script`

```bash
pipeline {
    agent any

    environment {
        repository ="awetumnn/a201-be-api" // Docker 이미지의 저장소 경로
        dockerImage = '' // Docker 이미지 변수 초기화

        registryCredential = 'dockerhub-access'

        releaseServerAccount = 'ubuntu'
        releaseServerUri = 'k10a201.p.ssafy.io'
        releasePort = '8080'
    }

    stages {
        stage('Git Clone') {
            steps {
                git branch: 'backend', // clone 받을 브랜치명
                credentialsId:'gitlab-user-access',
                url: 'https://lab.ssafy.com/s10-final/S10P31A201'
            }
        }
        stage('Jar Build') {
            steps {
                dir('./back/soup-api'){
                    sh 'chmod +x ./gradlew' // gradlew 파일에 실행 권한 부여
                    sh './gradlew clean bootJar' // Gradle로 JAR 파일 빌드
                }
            }
        }
        stage('Image Build & DockerHub Push') {
            steps {
                sh 'mkdir -p ../back/soup-api/'
                sh 'cp ./back/soup-api/build/libs/soup-api-0.0.1-SNAPSHOT.jar ../back/soup-api/'
                dir('./back/soup-api') {
                    script {
                        docker.withRegistry('', registryCredential) {
                            sh "docker buildx create --use --name mybuilder"
                            sh "docker buildx build --platform linux/amd64 -t $repository:$BUILD_NUMBER --push ."
                            sh "docker buildx build --platform linux/amd64 -t $repository:latest --push ."
                        }
                    }
                }

            }
        }
        stage('Before Service Stop') {
            steps {
                sshagent(credentials: ['ubuntu-a201']) {
                    sh '''
                        if
                            test "`ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri "docker ps -aq --filter ancestor=$repository:latest"`"; then
                            ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri "docker stop $(docker ps -aq --filter ancestor=$repository:latest)"
                            ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri "docker rm -f $(docker ps -aq --filter ancestor=$repository:latest)"
                            ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri "docker rmi $repository:latest"
                        fi
                    '''
                }
            }
        }
        stage('DockerHub Pull') {
            steps {
                sshagent(credentials: ['ubuntu-a201']) {
                    sh "ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri 'sudo docker pull $repository:latest'"
                }
            }
        }
        stage('Service Start') {
            steps {
                sshagent(credentials: ['ubuntu-a201']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no $releaseServerAccount@$releaseServerUri "sudo docker-compose up -d soup-be-api "
                    '''
                }
            }
        }
        stage('Service Check') {
            steps {
                sshagent(credentials: ['ubuntu-a201']) {
                    sh '''
                        #!/bin/bash

                        for retry_count in \$(seq 20)
                        do
                          if curl -s "https://back.so-up.store/api/actuator/health" > /dev/null
                          then
                              curl -d '{
                                          "text": "[BACKEND] Deployment Success",
                                          "attachments": [
                                            {
                                              "color": "good",
                                              "text": "BACKEND-DEV : The deployment was successful. The application is running smoothly."
                                            }
                                          ]
                                        }' -H "Content-Type: application/json" -X POST https://meeting.ssafy.com/hooks/hraztumtdbbzd8dft6q8mczywy
                              break
                          fi

                          if [ $retry_count -eq 20 ]
                          then
                            curl -d '{
                                        "text": "[BACKEND] Deployment Failure",
                                        "attachments": [
                                            {
                                              "color": "danger",
                                              "text": "BACKEND-DEV : The deployment failed. Please check the deployment logs for more information."
                                            }
                                        ]
                                    }' -H "Content-Type: application/json" -X POST https://meeting.ssafy.com/hooks/hraztumtdbbzd8dft6q8mczywy
                            exit 1
                          fi

                          echo "The server is not alive yet. Retry health check in 5 seconds..."
                          sleep 5
                        done
                    '''
                }
            }
        }
    }
}
```

`dockerfile` 작성

```docker
FROM openjdk:17-alpine
WORKDIR /usr/src/app
COPY ./build/libs/soup-api-0.0.1-SNAPSHOT.jar .
EXPOSE 8080

ENTRYPOINT ["java","-jar", "soup-api-0.0.1-SNAPSHOT.jar"]
```

### 3. Back 환경변수 설정

- application.properties

```yaml
## Server Configuration
server:
  port: 8080

spring:
  application:
    name: soup-api
  data:
    jdbc:
      repositories:
        enabled: false
    ## Redis Configuration
    redis:
      host: ${REDIS_HOST}
      port: ${REDIS_PORT}
      password: ${REDIS_PASSWORD}
      repositories:
        enabled: false
    ## MongoDB Configuration
    mongodb:
      uri: mongodb+srv://${MONGODB_USER}:${MONGODB_PASSWORD}@${MONGODB_NAME}.mongodb.net/${MONGODB_DATABASE}?authSource=admin&retryWrites=true&w=majority

  ## Database Configuration
  datasource:
    hikari:
      maximum-pool-size: 20
    url: jdbc:postgresql://${POSTGRESQL_URL}/${POSTGRESQL_DATABASE}
    username: ${POSTGRESQL_USER}
    password: ${POSTGRESQL_PASSWORD}
    driver-class-name: org.postgresql.Driver
  ## JPA/Hibernate Configuration
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
    defer-datasource-initialization: true
    generate-ddl: true
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${GMAIL_USERNAME}
    password: ${GMAIL_PASSWORD}
    properties:
      mail:
        smtp:
          auth: true
          timeout: 5000
          starttls:
            enable: true

  ## OAuth 2.0
  security:
    oauth2:
      client:
        registration: # 어떤 리소스 제공자의 클라이언트인가
          kakao:
            client-id: ${KAKAO_CLIENT_ID}
            client-secret: ${KAKAO_CLIENT_SECRET}
            scope: # 리소스 오너의 어떤 리소스(scope)가 필요한가
              - profile_nickname
              - account_email
              - profile_image
            client-name: kakao-login
            authorization-grant-type: authorization_code # 고정 값
            redirect-uri: ${KAKAO_BE_REDIRECT_URI} # BE로 redirect
            client-authentication-method: client_secret_post # http method
        provider: # 리소스 제공자(여기선 kakao)
          kakao:
            authorization-uri: https://kauth.kakao.com/oauth/authorize # 소셜 로그인 요청 시 여기로 redirect
            token-uri: https://kauth.kakao.com/oauth/token # 카카오에 액세스 가능한 카카오 액세스 토큰을 받아오기 위한 주소
            user-info-uri: https://kapi.kakao.com/v2/user/me # 유저 정보를 가져오기 위한 주소
            user-name-attribute: id

  servlet:
    multipart:
      max-file-size: 100MB
      max-request-size: 100MB

## social login
social-login:
  redirect-uri: ${SOCIAL_LOGIN_FE_REDIRECT_URI} # FE로 redirect (accessToken, refreshToken 붙여서)

## JWT
jwt:
  secret:
    key: ${JWT_SECRET_KEY}
  expire-time:
    access-token: ${ACCESS_TOKEN_DURATION}
    refresh-token: ${REFRESH_TOKEN_DURATION}
  issuer: ${JWT_ISSUER}

logging:
  level:
    org:
      springframework:
        data:
          mongodb:
            core:
              MongoTemplate: info

## Springdoc Configuration
springdoc:
  api-docs:
    path: /api/api-docs
    groups:
      enabled: true
  swagger-ui:
    path: /api/swagger-ui.html
    enabled: true
    groups-order: asc
    tags-sorter: alpha
    operations-sorter: alpha
    display-request-duration: true
    doc-expansion: none
  cache:
    disabled: true
  override-with-generic-response: false
  model-and-view-allowed: false
  default-consumes-media-type: application/json
  default-produces-media-type: application/json
  group-configs:
    - group: all-api
      paths-to-match:
        - /**
      paths-to-exclude:
        - /favicon.ico
        - /health
    - group: jwt-api
      paths-to-match:
        - /api/**
  show-actuator: true

## AWS S3 Configuration
cloud:
  aws:
    region:
      static: ap-northeast-2
    credentials:
      accessKey: ${AWS_ACCESS_KEY}
      secretKey: ${AWS_SECRET_KEY}
    s3:
      bucket: soup-bucket

management:
  server:
    port: 8080
  health:
    mail:
      enabled: false
  endpoints:
    web:
      base-path: /api/actuator
  endpoint:
    health:
      show-details: always # 상세 정보 표시
      status:
        http-mapping:
          down: 500 # server down 시 에러 정보
          out_of_service: 503
        order: DOWN, FATAL, OUT-OF-SERVICE, UNKNOWN, UP

# Claude
claude:
  api-key: ${CLAUDE_API_KEY}
  anthropic-version: 2023-06-01

# Feign
feign:
  claude:
    name: claude
    url: https://api.anthropic.com

# Liveblocks
liveblocks:
  base-url: https://api.liveblocks.io/v2
  secret-key: ${LIVEBLOCKS_SECRET_KEY}

# Openvidu Configuration
openvidu:
  url: ${OPENVIDU_SERVER_URL} #https://your-openvidu-server.com
  secret: ${OPENVIDU_SECRET_KEY}

# Spring Builder Root Path
springbuilder:
  source-path: ${PROJECT_SOURCE_PATH}
  domain-path: ${PROJECT_DOMAIN_PATH}
  global-path: ${PROJECT_GLOBAL_PATH}
  build-path: ${FILE_WRITE_PATH}

```

### 4. Build 단계에서 사용되는 파일

- `/src/main/resources`
    
    [빌드시 사용되는 파일.zip](https://prod-files-secure.s3.us-west-2.amazonaws.com/7fe93563-b680-4a21-8a23-42c5ad3b7a81/87479564-e032-49a3-8f62-3f854c79c25c/%E1%84%87%E1%85%B5%E1%86%AF%E1%84%83%E1%85%B3%E1%84%89%E1%85%B5_%E1%84%89%E1%85%A1%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%83%E1%85%AC%E1%84%82%E1%85%B3%E1%86%AB_%E1%84%91%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%AF.zip)
    

## 2. Nginx

### Nginx 설정

nginx 설정 파일 작성

- `/etc/nginx/conf.d` 경로에 `defaut.conf` 설정 파일을 만들어 아래의 코드를 입력합니다

```bash
server {
    listen 443 ssl;
    server_name k10a201.p.ssafy.io;
    
    ssl_certificate /etc/letsencrypt/live/k10a201.p.ssafy.io/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/k10a201.p.ssafy.io/privkey.pem;
    
    return 301 https://so-up.store;
}

server {
    listen 443 ssl;
    server_name back.so-up.store;

    ssl_certificate /etc/letsencrypt/live/so-up.store/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/so-up.store/privkey.pem;

    location / {
        proxy_pass http://soup-be-api:8080;
        proxy_set_header Host $host:$server_port;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;

        proxy_http_version 1.1;
        proxy_request_buffering off;
        proxy_buffering off;
    }

    location /ws-stomp {
        proxy_pass http://soup-be-api:8080/ws-stomp;

        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
    }

    # SSE를 위한 설정 추가
    location /api/notis/sub {
        proxy_pass http://soup-be-api:8080/api/notis/sub;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;

        proxy_http_version 1.1;
        proxy_set_header Connection '';
        proxy_buffering off;
        proxy_cache off;

        proxy_read_timeout 24h;
        proxy_connect_timeout 1h;
        proxy_send_timeout 1h;
    }

}

server {
    listen 443 ssl;
    server_name so-up.store www.so-up.store;

    ssl_certificate /etc/letsencrypt/live/so-up.store/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/so-up.store/privkey.pem;

    location /_next/static/chunks/ {
        alias /nginx/html/_next/static/chunks/;
        expires 365d;
        access_log off;
    }

    location /static/ {
        alias /nginx/html/public/; 
        expires 365d;
        access_log off;
    }

      location / {
          proxy_pass http://soup-front:3000;
          proxy_set_header Host $host:$server_port;
          proxy_set_header X-Real-IP $remote_addr;
          proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
          proxy_set_header X-Forwarded-Proto $scheme;

          proxy_http_version 1.1;
          proxy_request_buffering off;
          proxy_buffering off;
      }

}

server {
    listen 80;
    server_name www.so-up.store;
    return 301 https://$host$request_uri;
}

server {
    listen 8800;
    server_name k10a201.p.ssafy.io;

    location / {
        return 301 https://jenkins.so-up.store; 
    }
}

server {
    listen 443 ssl;
    server_name jenkins.so-up.store;

    ssl_certificate /etc/letsencrypt/live/so-up.store/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/so-up.store/privkey.pem;

    location / {
        proxy_pass http://k10a201.p.ssafy.io:8800;
        proxy_set_header Host $host:$server_port;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;

        proxy_http_version 1.1;
        proxy_request_buffering off;
        proxy_buffering off;
        add_header 'X-SSH-Endpoint' 'jenkins.so-up.store' always;
    }
}

```

## 3. Docker-compose

마지막으로 `Docker-compose.yml` 파일을  생성 후 아래의 코드를 입력합니다.

```docker
version: '3.8'
services:
  soup-be-api:
    image: awetumnn/a201-be-api:latest
    ports:
      - 8080:8080
    env_file:
      - ./env/be_a201.env
    volumes:
      - /home/ubuntu/build:/usr/src/app/build
    networks:
      - nat

  soup-front:
    image: awetumnn/a201-front:latest
    ports:
      - 3000:3000
    env_file:
      - ./env/fe_a201.env
    build:
      context: .
    volumes:
      - nextjs-static:/app/.next/static
      - public-files:/app/public

    networks:
      - nat

  redis:
    image: redis:latest
    ports:
      - 6379:6379
    volumes:
      - redis-data:/data
    networks:
      - nat

  nginx:
    image: nginx:latest
    ports:
      - 80:80
      - 443:443
    volumes:
      - /home/ubuntu/nginx/conf.d:/etc/nginx/conf.d # conf.d 폴더 마운트
      - /etc/letsencrypt:/etc/letsencrypt
      - nextjs-static:/nginx/html/_next/static # Next.js 정적 파일
      - public-files:/nginx/html/public # Next.js public 폴더
    depends_on:
      - soup-be-api
      - soup-front
    networks:
      - nat

networks:
  nat:
    external: true

volumes:
  redis-data:
  nextjs-static:
  public-files:

```

# 4. 빌드하기

위의 모든 과정을 완료하였다면 서비스를 실행할 수 있습니다.