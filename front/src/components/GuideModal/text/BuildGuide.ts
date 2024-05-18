export const BuildTitle = [
  'Step1. 빌드 안내',
  'Step2. 프로젝트 메타데이터 설정하기',
  'Step3. 빌드 미리보기',
  'Step4. 빌드하기',
  'Step5. 빌드파일 확인하기',
]

export const BuildText = [
  `
 - **빌드 단계에서는 작성한 ERD 및 API 명세서를 기반으로 Spring Project 파일이 생성됩니다.**

- 유의사항
    - **패키징 구조 :** 프로젝트는 도메인별로 구분되어 패키징됩니다.
    <details>
    <summary>패키징 예시</summary>
            
      
      com
        ㄴ example
            ㄴ duckbill
                ㄴ domain
                |   ㄴ member
                |   |   ㄴ api
                |   |   ㄴ application
                |   |   ㄴ dao
                |   |   ㄴ domain
                |   |   ㄴ dto
                |   |   ㄴ exception
                |   ㄴ restaurant
                |   |   ㄴ api
                |   |   ㄴ application
                |   |   ㄴ dao
                |   |   ㄴ domain
                |   |   ㄴ dto
                |   |   ㄴ exception
                |   ...
                ㄴ global
                |   ㄴ auth
                |   ㄴ common
                |   ㄴ config
                |   ㄴ error
                |   ㄴ infra
                |   ㄴ util
                |   ...
                ㄴ application
                |   ㄴ api
                |   ㄴ usecase
      
    <details>


- **기본 제공 디펜던시**: 기본으로 포함된 디펜던시는 제거할 수 없습니다.
    - 기본 제공 디펜던시
        - Lombok
        - Spring Data JPA
        - Validation
        - Swagger - Spring Doc
        - MySQL Drive
`,
  `
  - Spring 프로젝트의 메타데이터를 설정해 주세요.
  - 현재 설정 가능한 메타데이터
      - Build Tool : \`Gradle - Groovy\`
      - Language : \`Java17\`
      - Packaging : \`Jar\`
      - Spring Boot Version :
          - 3.3.0-SNAPSHOT
          - 3.3.0-RC1
          - 3.2.6-SNAPSHOT
          - 3.2.5
          - 3.1.12-SNAPSHOT
          - 3.1.11
      - Dependencies :

          \`\`\`
          💡 기본적으로 포함되는 dependency (제거 불가)
          
          - Lombok
          - Spring Data JPA
          - Validation
          - Swagger - Spring Doc
          - MySQL Drive
          \`\`\`
          
          \`\`\`
          💡 추가로 설정할 수 있는 depndency
          
          - Oracle Driver
          - Spring Data MongoDB
          - Mustache
          - OAuth2 Authorization Server
          - Thymeleaf
          - Spring Data for Apache Cassandra
          - Spring Data Reactive for Apache Cassandra
          - OAuth2 Resource Server
          - Spring Data Redis (Access+Driver)
          - Spring for Apache ActiveMQ 5
          - Apache Camel
          - Spring for Apache Kafka
          - Spring Boot DevTools
          - RSocket
          - Java Mail Sender
          - Quartz Scheduler
          - Spring Data Elasticsearch (Access+Driver)
          - Spring for RabbitMQ
          - MS SQL Server Driver
          - Spring Security
          - Spring for RabbitMQ Streams
          - Spring LDAP
          - OAuth2 Client
          - Spring Data R2DBC
          - WebSocket
          - Spring Integration
          - MariaDB Driver
          - Spring Data Reactive MongoDB
          - PostgreSQL Driver
          - Spring Data Reactive Redis
          - JDBC API
          - Spring Data JDBC
          - Spring Batch
          - MyBatis Framework
          - H2 Database
          \`\`\`
  `,
  `- 화면 하단 중앙의 '새로 빌드하기' 버튼을 클릭하면 생성될 프로젝트 파일의 미리보기가 표시됩니다. (*데이터 양이 많을 경우 로딩 시간이 길어질 수 있습니다.)
  - 프로젝트 파일 수정이 필요한 경우, ERD 및 API 단계로 돌아가 수정을 진행해 주세요.
  `,
  `
  - 생성될 프로젝트 파일을 확인한 후, 우측 상단의 '빌드 파일 다운로드' 버튼을 클릭해주세요.
  - 파일 다운로드가 완료되면 프로젝트 파일을 확인해주세요.
  `,
  `

  ![Untitled](https://file.notion.so/f/f/7fe93563-b680-4a21-8a23-42c5ad3b7a81/1935d93f-6ea4-4e0a-87c5-98142d597120/Untitled.png?id=13e312a6-ec7a-46a9-9057-46e07e854b0d&table=block&spaceId=7fe93563-b680-4a21-8a23-42c5ad3b7a81&expirationTimestamp=1716127200000&signature=rU8CQ1GH0xrVJ1mBH5iHF-6KZZCBqc2O7W3B2g2G05c&downloadName=Untitled.png)
  
  - 빌드 후에는 '빌드 파일 확인' 버튼이 활성화되며, 가장 최근에 빌드한 파일을 확인할 수 있습니다.`,
]
