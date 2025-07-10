@echo off
REM Build and run the application with Docker

echo Building Docker image...
docker build -t biztrips-app .

echo Starting application with Docker Compose...
docker-compose up --build -d app

echo Waiting for application to start...
timeout /t 30 /nobreak

echo Running Cypress tests...
docker-compose up --build cypress

echo Stopping application...
docker-compose down

echo Test run complete!
pause
