package com.example.sms.controller;

import com.example.sms.entity.Student;
import com.example.sms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The @RestController annotation is a game-changer! 
 * It tells Spring Boot that this class will handle incoming HTTP requests (GET, POST, etc.)
 * AND it will automatically convert our Java responses directly into JSON format!
 */
@RestController
/**
 * @RequestMapping defines the base URL for this entire controller.
 * So all endpoints here will start with "http://localhost:8080/api/students"
 */
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    // We inject the Service layer here, NOT the Repository.
    // The Controller -> talks to Service -> talks to Repository!
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * @GetMapping handles HTTP GET requests. 
     * Used for fetching/reading data.
     * URL: GET http://localhost:8080/api/students
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        // We get the list of students from the service and return it with a 200 OK status
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    /**
     * @PostMapping handles HTTP POST requests.
     * Used for creating brand new data.
     * @RequestBody takes the incoming JSON data from the request and turns it into a Java Student object!
     */
    @PostMapping
    public ResponseEntity<Student> registerNewStudent(@RequestBody Student student) {
        Student savedStudent = studentService.saveStudent(student);
        // We return the saved student along with a 201 CREATED status code
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    /**
     * @GetMapping("{id}") connects to URLs like "http://localhost:8080/api/students/5"
     * @PathVariable grabs that "5" from the URL and puts it into our 'studentId' variable!
     */
    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long studentId) {
        Student student = studentService.getStudentById(studentId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    /**
     * @PutMapping is used for entirely updating an existing record.
     */
    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long studentId, 
                                                 @RequestBody Student studentDetails) {
        
        // 1. First, fetch the existing student from the database
        Student existingStudent = studentService.getStudentById(studentId);
        
        // 2. Update all of the existing student's fields to match the incoming details
        existingStudent.setName(studentDetails.getName());
        existingStudent.setEmail(studentDetails.getEmail());
        existingStudent.setDob(studentDetails.getDob());
        // (Remember: Age is transient, so we don't save it!)

        // 3. Save the updated student back into the database
        Student updatedStudent = studentService.updateStudent(existingStudent);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    /**
     * @DeleteMapping is used for removing data.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long studentId) {
        studentService.deleteStudentById(studentId);
        return new ResponseEntity<>("Student deleted successfully!", HttpStatus.OK);
    }
}
