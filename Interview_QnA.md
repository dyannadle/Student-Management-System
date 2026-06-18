# Student Management System - Interview Questions & Answers

Based on the architecture and tech stack of your Spring Boot project (Spring Boot 3.2.4, Java 17, Spring Data JPA, MySQL), here is the comprehensive list of 50 interview questions and their answers.

---

## 🌟 Project Architecture & General Spring Boot

**1. Can you explain the architecture of your Student Management System?**  
**Answer:** The project follows a classic 3-tier layered architecture:
*   **Controller Layer:** Handles incoming HTTP requests and responses.
*   **Service Layer:** Contains the core business logic.
*   **Repository Layer (Data Access):** Interacts with the MySQL database using Spring Data JPA.

**2. Why did you choose Spring Boot for this project instead of just Spring Framework?**  
**Answer:** Spring Boot eliminates boilerplate configuration. It provides auto-configuration, starter POMs for easy dependency management, and an embedded server (like Tomcat), allowing me to focus entirely on writing business logic instead of XML or complex Java configurations.

**3. What is the significance of the `@SpringBootApplication` annotation?**  
**Answer:** It is a convenience annotation that combines three annotations:
*   `@Configuration`: Tags the class as a source of bean definitions.
*   `@EnableAutoConfiguration`: Tells Spring Boot to start adding beans based on classpath settings.
*   `@ComponentScan`: Tells Spring to look for other components, configurations, and services in the project package.

**4. How does Spring Boot auto-configuration work?**  
**Answer:** It automatically configures the application based on the dependencies present in the classpath. For example, because `spring-boot-starter-data-jpa` and `mysql-connector-j` are in the `pom.xml`, Spring Boot automatically configures a `DataSource`, `EntityManager`, and transaction manager without manual setup.

**5. Explain Inversion of Control (IoC) and Dependency Injection (DI).**  
**Answer:** IoC is a principle where the control of object creation is transferred to the Spring Container. DI is the implementation of IoC, where the Spring container "injects" required dependencies into a class (e.g., injecting a `Repository` into a `Service`) rather than the class instantiating it manually using the `new` keyword.

**6. What is the difference between `@Component`, `@Service`, `@Repository`, and `@Controller`?**  
**Answer:** 
*   `@Component`: Generic stereotype for any Spring-managed component.
*   `@Service`: Specialized `@Component` for business logic.
*   `@Repository`: Specialized `@Component` for data access; it also translates database exceptions into Spring's `DataAccessException`.
*   `@Controller`: Specialized `@Component` for the presentation layer.

**7. Why do we use an `application.properties` file?**  
**Answer:** It is used to externalize application configuration. In this project, it holds database connection properties (URL, username, password), Hibernate DDL-auto settings, and server configurations (like port numbers).

**8. Did you utilize any specific Java 17 features?**  
**Answer:** Java 17 is an LTS (Long Term Support) release. Features that can be utilized include `Records` (great for immutable DTOs), Text Blocks (useful for multi-line JSON or SQL strings), and enhanced Switch expressions.

**9. How do you handle dependencies in your project?**  
**Answer:** Dependencies are managed using Maven via the `pom.xml` file. It downloads the required libraries (like Spring Web, JPA, MySQL connector) from the central repository and manages version compatibility.

**10. What is the purpose of the `spring-boot-starter-parent` in your `pom.xml`?**  
**Answer:** It acts as the base configuration for a Spring Boot application. It provides default Java versions, default plugin configurations, and most importantly, dependency management—so we don't have to specify versions for common dependencies like `spring-boot-starter-web`.

---

## 🌐 Presentation Layer (REST API & Controllers)

**11. What is the role of the `StudentController` in your application?**  
**Answer:** It acts as the entry point for REST API calls from the client. It intercepts HTTP requests (GET, POST, etc.), extracts parameters, delegates business logic to the `StudentService`, and returns a structured HTTP response.

**12. What is the difference between `@Controller` and `@RestController`?**  
**Answer:** `@RestController` is a specialized version of `@Controller` that includes the `@ResponseBody` annotation. It ensures that the returned objects are automatically serialized into JSON or XML and written directly to the HTTP response body, rather than rendering a web view (like HTML/JSP).

**13. How do you map HTTP requests to your controller methods?**  
**Answer:** Using request mapping annotations: `@GetMapping` for retrieving data, `@PostMapping` for creating new data, `@PutMapping` for updating data, and `@DeleteMapping` for deleting data.

**14. What is the difference between `@PathVariable` and `@RequestParam`?**  
**Answer:** `@PathVariable` extracts values from the URI path itself (e.g., `/students/{id}`). `@RequestParam` extracts values from the query string parameters appended to the URL (e.g., `/students?id=1`).

**15. How did you handle the HTTP response status codes?**  
**Answer:** By returning a `ResponseEntity<T>` from the controller methods. This allows precise control over the status code (e.g., `ResponseEntity.status(HttpStatus.CREATED).body(dto)` for POST requests).

**16. What does the `@RequestBody` annotation do?**  
**Answer:** It tells Spring to deserialize the incoming HTTP request body (usually JSON) into a Java domain object (like a `StudentDto`) for use within the controller method.

**17. What makes your Student API "RESTful"?**  
**Answer:** It is stateless, utilizes standard HTTP methods (GET, POST, PUT, DELETE) appropriately, uses JSON for data transfer, and operates on resource-based URIs (e.g., `/api/students`).

**18. How would you handle pagination and sorting if records grow to millions?**  
**Answer:** Instead of returning `List<Student>`, I would use Spring Data's `Pageable` interface in the repository and controller to return a `Page<Student>`, passing page number, size, and sorting parameters.

---

## 🧠 Business Layer (Services)

**19. Why do we need a separate Service layer?**  
**Answer:** To separate business logic from the presentation layer (Controller) and database layer (Repository). This enforces the Single Responsibility Principle, makes the business logic reusable across multiple controllers, and makes the application easier to unit test.

**20. Why use an interface (`StudentService`) and an implementation class (`StudentServiceImpl`)?**  
**Answer:** It promotes loose coupling. The controller only depends on the interface contract, not the exact implementation. This makes swapping implementations easier and facilitates mocking in unit tests.

**21. How do you manage transactions in your service layer?**  
**Answer:** By using the `@Transactional` annotation. It ensures that all database operations within the method are executed as a single atomic transaction. If everything succeeds, it commits; if an error occurs, it rolls back.

**22. What happens if a runtime exception occurs inside a `@Transactional` method?**  
**Answer:** The transaction is automatically rolled back, meaning no partial data is committed to the database, maintaining data integrity.

**23. How do you handle dependency injection in your Service layer?**  
**Answer:** Constructor injection is preferred over field injection (`@Autowired`). It ensures that required dependencies are provided at instantiation, making the class immutable (by declaring dependencies `final`) and easier to test without a Spring context.

---

## 💾 Data Access Layer (JPA, Hibernate & Repository)

**24. What is Spring Data JPA, and how does it differ from Hibernate?**  
**Answer:** JPA (Java Persistence API) is a specification/interface for ORM in Java. Hibernate is an actual implementation of that specification. Spring Data JPA is an abstraction layer on top of JPA that significantly reduces boilerplate code required to implement data access layers.

**25. Can you explain the annotations on your `Student` entity class?**  
**Answer:** `@Entity` marks it as a JPA managed entity. `@Table` specifies the database table name. `@Id` marks the primary key field. `@GeneratedValue` dictates how the primary key is generated (e.g., auto-increment).

**26. What is the difference between `GenerationType.IDENTITY` and `GenerationType.AUTO`?**  
**Answer:** `IDENTITY` relies on the database's auto-increment feature (perfect for MySQL). `AUTO` allows the persistence provider (Hibernate) to pick an appropriate strategy, which often defaults to a sequence table.

**27. What does the `JpaRepository` interface provide?**  
**Answer:** By extending `JpaRepository`, the repository inherits standard CRUD methods (save, findById, findAll, delete) and methods for sorting and pagination, without me writing a single line of SQL or implementation code.

**28. How would you write a custom query to find a student by their email?**  
**Answer:** Spring Data JPA allows Query Methods: I can simply define a method `Optional<Student> findByEmail(String email);` in the repository interface, and Spring will automatically generate the SQL for it.

**29. What is the difference between `save()` and `saveAll()`?**  
**Answer:** `save()` persists or updates a single entity. `saveAll()` takes an Iterable (like a List) and persists multiple entities. `saveAll()` is usually more efficient as it can utilize JDBC batching.

**30. What is the N+1 select problem in Hibernate?**  
**Answer:** It occurs when querying a list of entities (1 query) and then subsequently executing N additional queries to fetch their lazily loaded associations. It is prevented using `JOIN FETCH` in JPQL or EntityGraphs.

**31. What is the difference between `FetchType.LAZY` and `FetchType.EAGER`?**  
**Answer:** `EAGER` loads related entity collections immediately along with the parent entity. `LAZY` defers loading the related collections until they are explicitly accessed via a getter method.

**32. How does Hibernate interact with the MySQL database?**  
**Answer:** Hibernate uses the MySQL JDBC driver to establish a connection. It translates JPQL and entity state changes into native MySQL SQL statements (using a configured SQL Dialect) and executes them against the database.

---

## 🛡️ Data Transfer Objects (DTO) & Mappers

**33. What is a DTO, and why use it instead of returning Entities?**  
**Answer:** A Data Transfer Object carries data between processes (e.g., from backend to frontend). Using DTOs decouples the database schema from the API contract, and allows you to format data specifically for the client.

**34. What security issues arise if you expose JPA Entities directly?**  
**Answer:** It can lead to exposing sensitive data (like passwords, audit dates) and "Mass Assignment" vulnerabilities where a malicious user could pass extra JSON fields to overwrite data they shouldn't have access to.

**35. How are you mapping `Student` entity to `StudentDto`?**  
**Answer:** It can be done manually via static methods in a `StudentMapper` class, or using automated libraries like `MapStruct` or `ModelMapper` which generate the mapping code at compile-time or run-time.

**36. If you update a DB field, what layers need to be updated?**  
**Answer:** You must update the `Entity` class, the `DTO` class, the `Mapper` logic, and potentially the `Service` and `Controller` if the business logic requires the new field.

**37. Can a single Entity have multiple DTOs?**  
**Answer:** Yes. For example, `StudentCreateDto` might require a password and email, while `StudentResponseDto` only returns the ID, Name, and Email, omitting sensitive information.

---

## 🛑 Exception Handling & Validation

**38. How did you implement input validation?**  
**Answer:** I added the `spring-boot-starter-validation` dependency and used Hibernate Validator annotations on the fields of my DTOs.

**39. What validation annotations did you use?**  
**Answer:** Examples include `@NotNull` (value cannot be null), `@NotBlank` (cannot be null or whitespace), `@Email` (validates email regex format), and `@Size(min=, max=)` for string length constraints.

**40. How do you trigger validation in the controller?**  
**Answer:** By adding the `@Valid` annotation next to the `@RequestBody` parameter in the controller method signature.

**41. How did you handle exceptions globally?**  
**Answer:** I created a Global Exception Handler class annotated with `@ControllerAdvice`. Inside it, methods annotated with `@ExceptionHandler(CustomException.class)` intercept specific exceptions thrown anywhere in the application.

**42. What happens if a user searches for an ID that doesn't exist?**  
**Answer:** The Service layer throws a custom `ResourceNotFoundException`. The `@ControllerAdvice` catches it and returns a formatted JSON response with a `404 NOT FOUND` HTTP status.

**43. How do you customize the error response payload?**  
**Answer:** I created a custom `ErrorDetails` class containing fields like `timestamp`, `message`, and `path`. The global exception handler populates this object and returns it as the HTTP response body.

---

## 🗄️ Database & MySQL

**44. Why did you choose MySQL for this project?**  
**Answer:** MySQL is a robust, widely used, open-source relational database. It is highly compatible with Spring Data JPA and is ideal for structured data like student records.

**45. How did you configure the database connection?**  
**Answer:** In `application.properties`, I provided:
*   `spring.datasource.url=jdbc:mysql://localhost:3306/student_db`
*   `spring.datasource.username=root`
*   `spring.datasource.password=password`

**46. What is the `spring.jpa.hibernate.ddl-auto` property?**  
**Answer:** It dictates how Hibernate manages the database schema. `update` updates the schema based on entities; `create` drops and recreates it every time. In production, it should be set to `none` or `validate`, and tools like Flyway or Liquibase should be used for database migrations.

---

## 🧪 Testing & Best Practices

**47. Did you write tests for your project?**  
**Answer:** Yes, utilizing JUnit 5 for the testing framework and Mockito for mocking dependencies to perform isolated unit tests.

**48. How do you mock the `StudentRepository` when testing the Service?**  
**Answer:** By declaring the repository with `@Mock` and the service with `@InjectMocks`. Then, using `Mockito.when(repository.findById(id)).thenReturn(Optional.of(student))` to simulate database behavior without hitting a real database.

**49. What is the difference between `@Mock` and `@InjectMocks`?**  
**Answer:** `@Mock` creates a dummy, simulated object. `@InjectMocks` creates an actual instance of the class being tested and automatically injects the `@Mock` created objects into it.

**50. How would you deploy this application to production?**  
**Answer:** 
1.  Package the app into a JAR file.
2.  Containerize the application using Docker.
3.  Set database configurations using environment variables rather than hardcoding in `application.properties`.
4.  Change `ddl-auto` to `validate` or `none`.
5.  Deploy to a cloud provider (like AWS EC2 or Elastic Beanstalk).
