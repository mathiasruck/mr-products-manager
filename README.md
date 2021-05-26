# MySQL database Startup

- Start MySQL database using `docker-compose up -d` at the root of the project

# Java application build and startup

- Run `./mvnw spring-boot:run` at the root folder of the project.
- Fist login URL: `/v1/login`
- Default username: `user`
- Default password: `password`

# Run Java tests

- Run `./mvnw clean test` at the root folder of the project.
- Test report will be accessible at `<project-root-folder>/target/site/jacoco/index.html`

# Build Java application

- Run `./mvnw clean package` at the root folder of the project.
- Artifact will be at `<project-root-folder>/target/mr-products-manager.jar`

# Rest API Documentation

- Navigate to `http://localhost:8080/swagger-ui.html` with the application running.

## UI Development server

- Run `./ng serve` at the root of the project for a dev server and navigate to `http://localhost:4200/` to access the application.