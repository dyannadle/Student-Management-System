# Student Management System

A robust, fully-commented Spring Boot REST API built to manage student records. This project was developed with a strong emphasis on best practices, including layered architecture (Three-Tier), Global Exception Handling, and Input Validation, making it an excellent resource for learning enterprise Java development.

## 🚀 Features
- **RESTful API:** Full CRUD operations for managing students.
- **Data Validation:** Enforces strict data integrity using Jakarta Validation (`@NotBlank`, `@Email`, `@Past`, etc.).
- **Global Error Handling:** Clean, predictable JSON responses for errors (e.g., `404 Not Found`, `400 Bad Request`) via `@ControllerAdvice`.
- **Three-Tier Architecture:** Clean separation of concerns between Controllers, Services, and Repositories.
- **MySQL Integration:** Automatically generated database tables via Hibernate.
- **CORS Enabled:** Ready to be consumed by any frontend framework (React, Angular, vanilla JS, etc.).

## 🛠️ Technologies Used
- **Language:** Java 17
- **Framework:** Spring Boot  
- **Database:** MySQL
- **Data Access:** Spring Data JPA / Hibernate
- **Build Tool:** Maven

## 📋 Prerequisites
Before you begin, ensure you have met the following requirements:
* You have installed **Java Development Kit (JDK) 17** or higher.
* You have installed **Maven**.
* You have a **MySQL** server running locally on port 3306.

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

## 📚 Learning Focus
This codebase is heavily commented line-by-line to explain exactly what each Spring Boot component does. It is structured to help you deeply understand:
* **`@RestController` & `@RequestMapping`**: How web traffic is managed and how JSON converts to Java.
* **`@Service` & `@Autowired`**: What "Dependency Injection" is and how it decouple logic.
* **`@Repository` & `JpaRepository`**: How Spring Data automatically writes and executes SQL queries.
* **`@Entity` & `@Table`**: How Java objects map directly to MySQL tables without writing schema migrations manually.
