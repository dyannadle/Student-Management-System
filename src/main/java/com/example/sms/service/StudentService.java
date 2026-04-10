package com.example.sms.service;

// Import our Student Entity.
import com.example.sms.entity.Student;

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
    List<Student> getAllStudents();

    /**
     * Save a new student to the database.
     * It expects you to pass in a 'Student' object, and it will return a 'Student' object when done.
     */
    Student saveStudent(Student student);

    /**
     * Find a single student by searching for their unique database ID.
     * It expects a 'Long' representing the ID, and returns the found 'Student'.
     */
    Student getStudentById(Long id);

    /**
     * Update an existing student's information.
     * It takes the modified 'Student' object, saves the changes over the old record, and returns the result.
     */
    Student updateStudent(Student student);

    /**
     * Permanently delete a student from the database using their unique ID.
     * Notice the return type is "void" because we don't need a response object, we just want it gone.
     */
    void deleteStudentById(Long id);
}
