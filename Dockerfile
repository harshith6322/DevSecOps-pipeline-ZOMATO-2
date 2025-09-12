FROM node:16-slim AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Production
FROM nginx:1.27.0-alpine AS prod
WORKDIR /usr/share/nginx/html
RUN rm -rf ./*
COPY --chown=nginx:nginx --from=build /app/dist .
EXPOSE 3000
CMD [ "ngnix", "-g" ,"daemon off;" ]
