# Building Temperature Management Application

This Spring Boot application provides a simple API for managing building temperature information.  It allows you to create, retrieve, update, and delete building records, each containing a name and a target temperature.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Building the Application](#building-the-application)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Database Configuration](#database-configuration)
- [Exception Handling](#exception-handling)
- [Code Structure](#code-structure)
- [Future Enhancements](#future-enhancements)
- [Contact](#contact)

## Introduction

This application is a demonstration project showcasing a basic REST API built with Spring Boot. It's designed to be a simple example of data management and can be extended to include more complex features in the future.

## Features

- Create new buildings with a name and target temperature.
- Retrieve building information by ID.
- Retrieve a list of all buildings.
- Update existing building information.
- Delete buildings.
- Handles exceptions like building not found or building already exists.
- Uses SQLite for data persistence (configurable).
- Includes unit and integration tests.
- Uses SpringDoc OpenAPI for API documentation (accessible at `/swagger-ui.html`).

## Technologies Used

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Web MVC
- SQLite
- Lombok
- JUnit Jupiter
- Mockito
- Jackson (for JSON handling)
- SpringDoc OpenAPI

## Building the Application

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/pbaritak/nordomatic-code-test.git
    ```

2.  **Navigate to the project directory:**

    ```bash
    cd nordomatic-code-test/my-spring-boot-app
    ```

3.  **Build the application using Gradle:**

    ```bash
    ./gradlew build
    ```

## Running the Application

1.  **Ensure you have Java 17 installed.**

2.  **Run the application using Gradle:**

    ```bash
    ./gradlew bootRun
    ```

3.  **The application will start on port 8080 (by default).**  You can access the API documentation at: `http://localhost:8080/swagger-ui.html`

## API Endpoints

| Method | Endpoint        | Description                                  |
| :----- | :-------------- | :------------------------------------------- |
| POST   | `/api/buildings` | Create a new building.                      |
| GET    | `/api/buildings` | Get all buildings.                          |
| GET    | `/api/buildings/{id}` | Get a building by ID.                       |
| PUT    | `/api/buildings/{id}` | Update a building by ID.                    |
| DELETE | `/api/buildings/{id}` | Delete a building by ID.                    |

## Testing

The application includes unit and integration tests.  Run the tests using Gradle:

```bash
./gradlew test
