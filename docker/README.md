# Task Management Tool – Dockerized Setup

This project includes a full-stack task management application consisting of:

- **Frontend**: React application (served under `/webapp`)
- **Backend**: Spring Boot application (served under `/api`)
- **Proxy**: Nginx reverse proxy for routing frontend and API requests

## 🐳 Prerequisites

Make sure you have the following installed:

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/)

---

## 📁 Project Structure

```
│── ...
│── docker/
│   │── backend/
│   │   │── Dockerfile
│   │── frontend/
│   │   │── Dockerfile
│   │── nginx-proxy/
│   │   │── html/
│   │   |   │── index.html
│   │   │── default.conf
│   │   │── nginx.conf
│   │── docker-compose.yml
│── ...
```

## 🚀 Run the application

Please navigate to the root folder (`<path-to-your-full-stack-project>/`) and start the applications:
```bash
docker-compose -f docker/docker-compose.yml up --build
```

This will build your `frontend` and `backend` images and run the those containers as well as the nginx-proxy.

If the images already exist, run the following command.
```bash
docker-compose -f docker/docker-compose.yml up
```

To stop the services:
```bash
docker-compose -f docker/docker-compose.yml down
```

## 🚀 Access to the application

- Frontend: http://localhost:8080/webapp
- Backend API: http://localhost:8080/api

⚙️ Nginx Reverse Proxy
The file `docker/nginx-proxy/default.conf` contains the Routing defintions:
```nginx configuration
    set $frontend "http://tm-frontend-webapp:80/";
    location /webapp/ {
        proxy_pass $frontend;
    }

    set $backend "http://tm-backend-api:8080/";
    location /api/ {
        proxy_pass $backend;
    }
```