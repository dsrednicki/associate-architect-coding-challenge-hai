# Associate-Architect-Coding-Challenge


## Setup

### Backend Folder

The backend folder contains the main application code for the project. It is structured as follows:

- `com.signavio.architect.challenge`: This is the base package for the Java source code.
    - `rest`: Contains an example REST controller for handling HTTP requests.
    - `repository`: Contains an example repository for data access.
    - `ChallengeApplication.java`: The main class for running the Spring Boot application.

Example tests are available for the Repository and RestController.

### Running the Application with Gradle

To run the application using Gradle, follow these steps:

1. Open a terminal and navigate to the `backend` folder.
2. Use the following command to build and run the application:

```sh
./gradlew bootRun
```

Once the application is running, you can access the endpoint via http://localhost:8080/tasks.

### Running Tests with Gradle

To run the tests using Gradle, follow these steps:

1. Open a terminal and navigate to the `backend` folder.
2. Use the following command to run the tests:

```sh
./gradlew test
```
