FROM node:20.11.1 AS builder
ARG NEXT_PUBLIC_LIVEBLOCKS_PUBLIC_KEY
ENV NEXT_PUBLIC_LIVEBLOCKS_PUBLIC_KEY $NEXT_PUBLIC_LIVEBLOCKS_PUBLIC_KEY
WORKDIR /app
COPY package*.json pnpm-lock.yaml ./
RUN npm install -g pnpm
RUN pnpm install
COPY . .
RUN pnpm run build

FROM node:20.11.1
WORKDIR /app
COPY --from=builder /app/.next/standalone ./
COPY --from=builder /app/.next/static ./.next/static
COPY --from=builder /app/public ./public  # 복사 추가: public 폴더 내용 복사
EXPOSE 3000
CMD ["node", "server.js"]
