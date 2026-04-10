package com.example.sms.repository;

// Import our custom Student Entity.
import com.example.sms.entity.Student;
// Import the JpaRepository which provides all the heavy-lifting database methods out-of-the-box.
import org.springframework.data.jpa.repository.JpaRepository;
// Import Repository annotation to declare this as a DAO (Data Access Object).
import org.springframework.stereotype.Repository;

// Import Optional, which helps us handle cases where a database row isn't found (avoiding NullPointerExceptions).
import java.util.Optional;

/**
 * The @Repository annotation tells Spring that this is a Data Access object.
 * It registers this Interface in the Spring Context so it can be @Autowired into the Service Layer.
 * It also catches database-specific errors and converts them into Spring's standard DataAccessException format.
 */
@Repository
// We extend "JpaRepository". 
// The <Student, Long> inside the angle brackets are "Generics". 
// They tell Spring Data JPA: "This Repository manages the 'Student' Entity, and the Primary Key (@Id) is of type 'Long'."
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * This is called a "Custom Query Method" or a "Derived Query".
     * By extending JpaRepository, we already get methods like .findAll(), .findById(), and .save() for free.
     * But what if we want to search by Email instead of by ID?
     * 
     * Because we followed an incredibly specific naming convention (find... By... Email), 
     * Spring is smart enough to parse the name of this method!
     * It will automatically write and execute an SQL query like: 
     * "SELECT * FROM students WHERE email = ?"
     * 
     * We don't have to write ANY implementation code—Spring Data JPA writes the implementation for us!
     * Returning an "Optional<Student>" means it will safely return an empty result if the email is not found.
     */
    Optional<Student> findStudentByEmail(String email);
}
