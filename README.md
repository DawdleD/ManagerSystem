# ManagerSystem Project Documentation

## Introduction
ManagerSystem is a management system built on Spring Boot, offering user management and access control features.

## Configuration Details
The project's configuration file is located at `src/main/resources/application.properties`, with key configurations as follows:

- `spring.application.name`: The name of the application, used to identify the application instance.
- `user.endpoints.data.file.path`: The storage path for user endpoint data files, used for persisting user endpoint information.
- `logging.config`: Specifies the location of the logging configuration file, used to configure logging behavior.
- `user.context.header`: The name of the HTTP header field used to pass user role information. This is crucial for identifying user identity and enforcing access control based on user roles.

## Quick Start

### Prerequisites
- JDK 1.8 or higher
- Maven 3.3 or higher

### Building the Project
To build the project, execute the following command in the project root directory:

```shell
mvn clean install
```

### Running the Project
After building, start the project with the following command:

```shell
java -jar target/manager-system-0.0.1-SNAPSHOT.jar
```

Alternatively, you can use the Maven Spring Boot plugin:

```shell
mvn spring-boot:run
```

## Feature Overview

### User Management
- `/admin/addUser`: Adds a user, requires admin privileges.
- `/user/{resource}`: Retrieves resource information based on resource type and user ID(extracted from HTTP header), requires user or admin privileges.

### Access Control
The project implements access control through AOP technology and the custom annotation `@PermissionCheck`, which determines whether a user has access to specific interfaces based on user roles and annotations on request methods.

## Logging Configuration
The logging configuration file is located at `src/main/resources/log4j2.xml`, where you can adjust the log level and output format as needed.

## Data Persistence
User endpoint data is persisted through local JSON files, with the file path specified by the `user.endpoints.data.file.path` configuration item.

## Security Notice
- Avoid exposing sensitive configuration information in public environments.
- Regularly update dependencies to fix known security vulnerabilities.
- Use HTTPS in production environments to enhance the security of data transmission.

## Contact
For any questions or suggestions, please submit through the project's Issue tracking system.