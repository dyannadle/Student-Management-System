package com.example.sms.service.impl;

// Import the Student Entity so we can use it as a data type.
import com.example.sms.entity.Student;
// Import the StudentRepository interface to enable database access.
import com.example.sms.repository.StudentRepository;
// Import the StudentService interface that this class will implement.
import com.example.sms.service.StudentService;
// Import our custom custom exception to throw when a student is not found.
import com.example.sms.exception.ResourceNotFoundException;
// Import Autowired to allow Spring to automatically inject dependencies.
import org.springframework.beans.factory.annotation.Autowired;
// Import Service to declare this class as a Spring Service component.
import org.springframework.stereotype.Service;

// Import List interface to work with collections of students.
import com.example.sms.dto.StudentDto;
import com.example.sms.mapper.StudentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The @Service annotation is crucial here! 
 * It tells Spring Boot: "This class holds the business logic. Please create an instance of it automatically."
 * It registers this class as a "Bean" in the Spring context.
 */
@Service
public class StudentServiceImpl implements StudentService {

    // Declare a private, final field for the repository.
    // 'final' ensures it cannot be changed once it is initialized.
    // We need the Repository so our Service can interact with the Database!
    private final StudentRepository studentRepository;

    /**
     * The @Autowired annotation tells Spring to automatically inject an instance of StudentRepository 
     * into this constructor for us. We don't have to manually execute "new StudentRepository()".
     * This concept is called "Dependency Injection".
     */
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        // Assign the injected repository to our class-level variable.
        this.studentRepository = studentRepository;
    }

    // @Override ensures we are correctly implementing the method from the StudentService interface.
    @Override
    public Page<StudentDto> getAllStudents(Pageable pageable) {
        Page<Student> studentsPage = studentRepository.findAll(pageable);
        
        return studentsPage.map(StudentMapper::mapToStudentDto);
    }

    // @Override ensures correct implementation.
    @Override
    public StudentDto saveStudent(StudentDto studentDto) {
        // Step 1: Convert the incoming DTO into a Database Entity so JPA can read it.
        Student student = StudentMapper.mapToStudent(studentDto);
        
        // Step 2: Calling .save() on the repository takes the Java object and writes it into the database table!
        // It converts the Java properties into a "INSERT INTO students ..." SQL query.
        Student savedStudent = studentRepository.save(student);
        
        // Step 3: Convert the newly saved Entity (which now has an automatically generated ID) back into a DTO to return.
        return StudentMapper.mapToStudentDto(savedStudent);
    }

    // @Override ensures correct implementation.
    @Override
    public StudentDto getStudentById(Long id) {
        // .findById(id) searches the database for a specific Primary Key.
        // It returns an "Optional" object, which means the database MIGHT find it, or it MIGHT NOT!
        // If the ID doesn't exist, the .orElseThrow() method gets triggered...
        Student student = studentRepository.findById(id).orElseThrow(
                // ...and we throw our brand new, custom ResourceNotFoundException!
                // We pass in "Student" (the resource), "Id" (the field), and id (the value that failed).
                () -> new ResourceNotFoundException("Student", "Id", id)
        );
        return StudentMapper.mapToStudentDto(student);
    }

    // @Override ensures correct implementation.
    @Override
    public StudentDto updateStudent(StudentDto studentDto) {
        // Convert the updated DTO back into an Entity.
        Student student = StudentMapper.mapToStudent(studentDto);
        
        // The .save() method works for generating a new row AND updating an existing row.
        // If the passed 'student' object already has an ID that exists in the database, 
        // JPA recognizes it and executes an "UPDATE students SET ... WHERE id = ?" query instead of INSERT.
        Student updatedStudent = studentRepository.save(student);
        
        // Return the successfully updated data as a DTO.
        return StudentMapper.mapToStudentDto(updatedStudent);
    }

    // @Override ensures correct implementation.
    @Override
    public void deleteStudentById(Long id) {
        // We simply call .deleteById() and pass the specific student's ID.
        // Behind the scenes, it executes a "DELETE FROM students WHERE id = ?" SQL query.
        studentRepository.deleteById(id);
    }
}
