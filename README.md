# Film in Armenia

Full-stack web application for discovering filming locations and nearby services in Armenia.

## Stack

- React frontend
- Spring Boot 3 / Java 17 backend
- PostgreSQL
- Docker Compose

## Run with Docker

1. Copy `.env.example` to `.env` and replace every `change-me` value.
2. Start the application:

   ```bash
   docker compose up --build
   ```

The frontend is exposed on port `80`, the backend on port `5101`, pgAdmin on port `5050`, and File Browser on port `8082`.

## Development

Run the backend from `backend` with `./gradlew bootRun`. Run the frontend from `frontend` with `npm install` and `npm start`.

Deployment is available as a manually triggered GitHub Actions workflow and requires the repository secrets referenced in `.github/workflows/deploy.yml`.
