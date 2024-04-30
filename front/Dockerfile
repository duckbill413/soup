FROM node:20.11.1 AS builder
WORKDIR /app
COPY package*.json pnpm-lock.yaml ./
COPY .env.production ./.env
RUN npm install -g pnpm
RUN pnpm install
COPY . .
RUN pnpm run build

FROM node:20.11.1
WORKDIR /app
COPY --from=builder /app/.next/standalone ./
COPY --from=builder /app/.next/static ./.next/static
COPY --from=builder /app/.env.production ./.env 
EXPOSE 3000
CMD ["node", "server.js"]
