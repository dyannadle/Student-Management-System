# 🎓 Student Management System

![Java 17](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.4-brightgreen.svg)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue.svg)
![REST API](https://img.shields.io/badge/REST-API-red.svg)

A robust, enterprise-grade Spring Boot REST API built to manage student records. This project was developed with a strong emphasis on industry best practices, making it an excellent resource for learning backend Java development.

## 🚀 Key Features & Best Practices
- **RESTful API:** Full CRUD operations for managing students.
- **Three-Tier Architecture:** Clean separation of concerns between Controllers, Services, and Repositories.
- **DTO Pattern:** Utilizes Data Transfer Objects (DTOs) and Mappers to ensure secure data transfer and decouple API contracts from the database schema.
- **Data Validation:** Enforces strict data integrity using Jakarta Validation (`@NotBlank`, `@Email`, etc.).
- **Global Error Handling:** Clean, predictable JSON responses for errors (e.g., `404 Not Found`, `400 Bad Request`) via `@ControllerAdvice` and `@ExceptionHandler`.
- **MySQL Integration:** Automatically generated database tables via Hibernate/Spring Data JPA.

## 📂 Project Structure

```text
src/main/java/com/example/sms/
├── controller    # Handles HTTP requests & REST endpoints
├── dto           # Data Transfer Objects for API requests/responses
├── entity        # JPA Entities representing database tables
├── exception     # Custom exceptions & Global Exception Handler
├── mapper        # Maps between Entities and DTOs
├── repository    # Spring Data JPA interfaces for database interaction
├── service       # Core business logic and transaction management
└── StudentManagementSystemApplication.java
```

## 🛠️ Technologies Used
- **Language:** Java 17
- **Framework:** Spring Boot 3.2.4
- **Database:** MySQL
- **Data Access:** Spring Data JPA / Hibernate
- **Build Tool:** Maven

## 📋 Prerequisites
Before you begin, ensure you have met the following requirements:
* You have installed **Java Development Kit (JDK) 17** or higher.
* You have installed **Maven**.
* You have a **MySQL** server running locally on port `3306`.

## ⚙️ Setup and Installation

1. **Configure the Database**
   Open `src/main/resources/application.properties` and ensure the MySQL credentials match your local setup:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/student_management_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=Deepak@2002
   ```
   *(Note: We passed `createDatabaseIfNotExist=true` so Spring Boot will automatically create the database for you!)*

2. **Run the Application**
   Open your terminal/command prompt and navigate to the project directory, then run:
   ```bash
   mvn clean compile
   mvn spring-boot:run
   ```
   The application will successfully start on `http://localhost:8080`.

## 🌐 API Endpoints

Test the API using an application like **Postman** or your internet browser!

| HTTP Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/students` | Retrieve a list of all students |
| `POST` | `/api/students` | Register a new student |
| `GET` | `/api/students/{id}` | Get a specific student by ID |
| `PUT` | `/api/students/{id}` | Update an existing student |
| `DELETE` | `/api/students/{id}` | Delete a student |

### Example POST Payload
To register a new student, send a `POST` request to `http://localhost:8080/api/students` with this raw JSON Body:
```json
{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "dob": "2000-05-15"
}
```

## 📝 Interview Preparation
A comprehensive set of 50 interview questions and answers based on this specific project has been compiled. It covers Architecture, Spring Boot Core, JPA, DTOs, and Exception Handling.
👉 **[View the Interview Q&A Document](Interview_QnA.md)**

## 📚 Learning Focus
This codebase is structured to help you deeply understand:
* **`@RestController` & HTTP Methods**: How web traffic is managed and how JSON converts to Java.
* **DTOs & Object Mapping**: Why we shouldn't expose internal database entities directly to the client.
* **`@Service` & Dependency Injection**: What "Dependency Injection" is and how it decouples logic.
* **`@Repository` & Spring Data JPA**: How Spring Data automatically writes and executes SQL queries.
* **Global Exception Handling**: How to intercept exceptions centrally and return user-friendly error messages.
