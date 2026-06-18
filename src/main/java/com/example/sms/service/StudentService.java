package com.example.sms.service;

import com.example.sms.dto.StudentDto;

// Import List to handle returning multiple students at once.
import java.util.List;

/**
 * The Service Interface.
 * 
 * In Java, an Interface is like a "Contract" or a "Menu". 
 * We define WHAT actions our system can perform with a Student, but we do NOT write HOW they are done here.
 * Notice that none of the methods below have curly braces { } or logic inside them.
 * 
 * Why do this? This is a standard Java best practice for producing "Loosely Coupled" code. 
 * The Controller only cares that a method exists; it doesn't care how it works!
 */
public interface StudentService {

    /**
     * Retrieve a list of all students currently in the database.
     * We don't say HOW it fetches them, just that it MUST return a List of Students.
     */
    List<StudentDto> getAllStudents();

    /**
     * Save a new student to the database.
     * It expects you to pass in a 'StudentDto' object, and it will return a 'StudentDto' object when done.
     */
    StudentDto saveStudent(StudentDto studentDto);

    /**
     * Find a single student by searching for their unique database ID.
     * It expects a 'Long' representing the ID, and returns the found 'StudentDto'.
     */
    StudentDto getStudentById(Long id);

    /**
     * Update an existing student's information.
     * It takes the modified 'StudentDto' object, saves the changes over the old record, and returns the result.
     */
    StudentDto updateStudent(StudentDto studentDto);

    /**
     * Permanently delete a student from the database using their unique ID.
     * Notice the return type is "void" because we don't need a response object, we just want it gone.
     */
    void deleteStudentById(Long id);
}
