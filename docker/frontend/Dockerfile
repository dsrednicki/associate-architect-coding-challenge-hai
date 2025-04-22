# Use the official Node.js runtime as the base image
FROM node:20-alpine AS builder

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY frontend/ ./frontend
WORKDIR /app/frontend

# Install dependencies
RUN npm install

# Copy the entire application code to the container
#COPY frontend/public ./public
#COPY frontend/src ./src
#COPY frontend/.env ./.env

# Build the React app for production
RUN npm run build

# Use Nginx as the production server
FROM nginxinc/nginx-unprivileged:1.27-bookworm

# Copy the built React app to Nginx's web server directory
COPY --from=builder /app/frontend/build /usr/share/nginx/html/webapp

# Expose port 80 for the Nginx server
EXPOSE 80

# Start Nginx when the container runs
CMD ["nginx", "-g", "daemon off;"]