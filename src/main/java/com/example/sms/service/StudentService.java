package com.example.sms.service;

import com.example.sms.entity.Student;
import java.util.List;

/**
 * The Service Interface.
 * We define WHAT actions our system can perform with a Student without detailing HOW they are done here.
 * This is a standard Java best practice for producing "Loosely Coupled" code.
 */
public interface StudentService {

    /**
     * Retrieve a list of all students.
     */
    List<Student> getAllStudents();

    /**
     * Save a new student to the database.
     */
    Student saveStudent(Student student);

    /**
     * Find a single student by their database ID.
     */
    Student getStudentById(Long id);

    /**
     * Update an existing student's information.
     */
    Student updateStudent(Student student);

    /**
     * Delete a student by their database ID.
     */
    void deleteStudentById(Long id);
}
