FROM node:20.11.1
WORKDIR /app
COPY package*.json pnpm-lock.yaml ./
RUN npm install -g pnpm
RUN pnpm install
RUN rm -rf node_modules
COPY . .
RUN pnpm run build
EXPOSE 3000

CMD ["npm", "start"]