package com.example.sms.repository;

import com.example.sms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * The @Repository annotation tells Spring that this is a Data Access object.
 * It also enables Spring to catch database-specific exceptions and re-throw them 
 * as portable Spring Data Exceptions.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * This is a "Custom Query Method".
     * Even though we don't write SQL, Spring is smart enough to parse this name!
     * It will automatically generate a query like: 
     * SELECT * FROM students WHERE email = ?
     */
    Optional<Student> findStudentByEmail(String email);
}
