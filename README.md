
# User Management API

This project is a simple User Management API built using **Spring Boot**. It supports operations like creating, updating, retrieving, and deleting users while enforcing validation rules for email and phone numbers. It also prevents the insertion of duplicate records based on email and phone number.

## Features

- **CRUD operations**: 
  - Create new users
  - Retrieve users by ID or list all users
  - Update existing users
  - Delete users by ID
- **Validation**: 
  - Validates email format (`@Email`).
  - Validates that the phone number is exactly 10 digits long (`@Pattern`).
- **Duplicate Prevention**:
  - Ensures that no two users can have the same email or phone number.
- **Custom Exception Handling**: 
  - Provides user-friendly error messages for validation errors, duplicate entries, and database constraint violations.

## Technologies Used

- **Spring Boot** for building the REST API.
- **Spring Data JPA** for database interactions.
- **H2 Database** (in-memory and file-based modes) for development.
- **JUnit 5** and **Mockito** for unit testing.
- **Bean Validation** (JSR 303) for request validation.

## Getting Started

### Prerequisites

- **Java 11** or later
- **Maven** 3.6+ installed

### Clone the Repository

```bash
git clone https://github.com/aj3011/user-management-api.git
cd user-management-api
```

### Build and Run the Project

1. Build the project with Maven:

    ```bash
    mvn clean install
    ```

2. Run the Spring Boot application:

    ```bash
    mvn spring-boot:run
    ```

### Access the H2 Database Console

Once the application is running, you can access the H2 database console by navigating to:

```
http://localhost:8081/h2-console
```

- **JDBC URL**: `jdbc:h2:file:./data/mydb` (for file-based configuration)
- **Username**: (leave it blank)
- **Password**: (leave it blank)

### API Endpoints

#### 1. **Create a New User**

- **POST** `/users`
- Request body (JSON):
    ```json
    {
        "email": "test@example.com",
        "phoneNumber": "1234567890",
        "firstName": "John",
        "lastName": "Doe",
        "username": "johndoe"
    }
    ```
- Responses:
  - **201 Created**: User created successfully.
  - **409 Conflict**: Email or phone number already exists.
  - **400 Bad Request**: Invalid input data.

#### 2. **Get User by ID**

- **GET** `/users/{id}`
- Responses:
  - **200 OK**: Returns the user data.
  - **404 Not Found**: User with the given ID not found.

#### 3. **Get All Users**

- **GET** `/users`
- Responses:
  - **200 OK**: Returns the list of users.
  - **204 No Content**: No users available.

#### 4. **Update User by ID**

- **PUT** `/users/{id}`
- Request body (JSON):
    ```json
    {
        "email": "newemail@example.com",
        "phoneNumber": "0987654321",
        "firstName": "John",
        "lastName": "Doe",
        "username": "johndoe"
    }
    ```
- Responses:
  - **200 OK**: User updated successfully.
  - **404 Not Found**: User with the given ID not found.
  - **409 Conflict**: Email or phone number already exists.

#### 5. **Delete User by ID**

- **DELETE** `/users/{id}`
- Responses:
  - **204 No Content**: User deleted successfully.
  - **404 Not Found**: User with the given ID not found.

## Validation Rules

- **Email** must be in a valid email format (e.g., `user@example.com`).
- **Phone number** must be exactly 10 digits, with no spaces or hyphens.
- **Email** and **phone number** must be unique across all users.

## Unit Testing

The project includes unit tests written with **JUnit 5** and **Mockito**. To run the tests, use:

```bash
mvn test
```

Tests cover the following scenarios:
- Creating a user (with and without duplicates).
- Validating email and phone number formats.
- Handling `DataIntegrityViolationException` for database constraints.

## Configuration

### Database Configuration

The default database being used is H2 ( file-based configuration)

It allows the data to be persisted across application restarts ( specified as a requirement )

Following is the file-based configuration for H2 database for this project:

```properties
spring.application.name=userInfo-app
server.port=8081
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:file:./data/mydb;AUTO_SERVER=TRUE
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
spring.jpa.defer-datasource-initialization=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

## Error Handling

- **400 Bad Request**: For validation errors (invalid email, phone number, etc.).
- **409 Conflict**: For duplicate email or phone number.
- **500 Internal Server Error**: For unexpected server errors, including database constraint violations.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
