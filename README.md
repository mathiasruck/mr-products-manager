# MySQL database Startup

- Start MySQL database using `docker-compose up -d` at the root of the project

# Java application build and startup

- Run `./mvnw spring-boot:run` at the root folder of the project.

# Rest API Documentation

- Navigate to `http://localhost:8080/swagger-ui.html`

## Development server

- Run `./ng serve` at the root of the project for a dev server and navigate to `http://localhost:4200/` to access the application.

## Build

- Run `./ng build` at the root of the project to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

- Run `./ng test` at the root of the project to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

- Run `./ng e2e` at the root to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).