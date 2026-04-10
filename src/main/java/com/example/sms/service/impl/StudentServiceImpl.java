package com.example.sms.service.impl;

import com.example.sms.entity.Student;
import com.example.sms.repository.StudentRepository;
import com.example.sms.service.StudentService;
import com.example.sms.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The @Service annotation is crucial here! 
 * It tells Spring Boot: "This class holds the business logic. Please create an instance of it automatically."
 */
@Service
public class StudentServiceImpl implements StudentService {

    // We need the Repository so our Service can interact with the Database!
    private final StudentRepository studentRepository;

    /**
     * The @Autowired annotation tells Spring to automatically inject the StudentRepository 
     * into this constructor for us. We don't have to manually create with "new StudentRepository()".
     */
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        // The repository gives us the magical "findAll()" method to get all DB rows!
        return studentRepository.findAll();
    }

    @Override
    public Student saveStudent(Student student) {
        // Here we could add business logic. 
        // e.g. checking if the email already exists before saving!
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        // If the ID doesn't exist, we throw our brand new ResourceNotFoundException!
        return studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student", "Id", id)
        );
    }

    @Override
    public Student updateStudent(Student student) {
        // The .save() method works for both creating AND updating. 
        // If the student already has an ID, JPA knows to UPDATE that row instead of making a new one.
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(Long id) {
        // Ask the repository to delete the database row matching this ID
        studentRepository.deleteById(id);
    }
}
