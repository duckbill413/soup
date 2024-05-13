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
