package com.example.sms.controller;

// Import our Student Entity.
import com.example.sms.entity.Student;
// Import the StudentService interface to access our business logic.
import com.example.sms.service.StudentService;
// Import the Autowired annotation for dependency injection.
import org.springframework.beans.factory.annotation.Autowired;
// Import HttpStatus to return correct network response codes (like 200 OK or 201 CREATED).
import org.springframework.http.HttpStatus;
// Import ResponseEntity, which allows us to customize both the data returned AND the HTTP status code.
import org.springframework.http.ResponseEntity;
// Import all web annotations (like @GetMapping, @PostMapping, etc.).
import org.springframework.web.bind.annotation.*;
// Import @Valid to trigger input data validation.
import jakarta.validation.Valid;

// Import List to handle collections of students.
import java.util.List;

/**
 * @CrossOrigin(origins = "*") is a security setting!
 * Web browsers block front-ends from making API calls to different URLs to prevent hacking.
 * This annotation disables that block, allowing any frontend ("*") to access this backend.
 */
@CrossOrigin(origins = "*")
/**
 * @RestController is actually two annotations combined into one: @Controller and @ResponseBody.
 * It tells Spring Boot that this class will handle incoming web requests (GET, POST, etc.),
 * AND it automatically transforms Java returned data into JSON format for the internet.
 */
@RestController
/**
 * @RequestMapping defines the base URL for this entire controller.
 * So all endpoints defined below will start with "http://localhost:8080/api/students".
 */
@RequestMapping("/api/students")
public class StudentController {

    // Define a private, final variable to hold our Service layer component.
    private final StudentService studentService;

    /**
     * @Autowired tells Spring to find our StudentServiceImpl and inject it right here.
     * NOTICE: The Controller talks to the Service, NOT to the Repository! (This is Three-Tier Architecture).
     */
    @Autowired
    public StudentController(StudentService studentService) {
        // Assign the injected service to our private variable so we can use it.
        this.studentService = studentService;
    }

    /**
     * @GetMapping connects HTTP GET requests (like loading a webpage) to this method.
     * Because of the class-level @RequestMapping, this handles requests to "http://localhost:8080/api/students"
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        // We call our service to get the giant list of students.
        // We wrap that list inside a ResponseEntity along with a "200 OK" success status code!
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    /**
     * @PostMapping connects HTTP POST requests (used for submitting data) to this method.
     * @Valid forces Spring to look at the @NotBlank and @Email rules we created in Student.java before allowing the code to run.
     * @RequestBody takes the incoming raw JSON string and automatically parses it into a Java Student object!
     */
    @PostMapping
    public ResponseEntity<Student> registerNewStudent(@Valid @RequestBody Student student) {
        // We pass the new, valid Student object to our service layer to be saved into the MySQL database.
        Student savedStudent = studentService.saveStudent(student);
        // We return the newly saved student object along with a "201 CREATED" status code.
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    /**
     * @GetMapping("{id}") connects to URLs ending in a number, like "http://localhost:8080/api/students/5".
     * @PathVariable automatically grabs whatever number is placed in the {id} spot and hands it to us as a Long variable!
     */
    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long studentId) {
        // We ask the service layer to fetch the student with this specific ID.
        Student student = studentService.getStudentById(studentId);
        // If successful, return the finding as JSON with a 200 OK.
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    /**
     * @PutMapping connects HTTP PUT requests (used for fully updating a record) to this method.
     * We need BOTH an {id} from the URL and a JSON @RequestBody to define the new data.
     */
    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long studentId, 
                                                 @Valid @RequestBody Student studentDetails) {
        
        // 1. First, fetch the existing student from the database using their ID. 
        // (If they don't exist, this throws an error and the method instantly stops!)
        Student existingStudent = studentService.getStudentById(studentId);
        
        // 2. Overwrite the old properties with the new incoming properties manually.
        existingStudent.setName(studentDetails.getName());
        existingStudent.setEmail(studentDetails.getEmail());
        existingStudent.setDob(studentDetails.getDob());
        // (Remember: Age is transient and automatically calculated, so we never save it!)

        // 3. Save the modified student back into the database.
        Student updatedStudent = studentService.updateStudent(existingStudent);
        // Return the modified student as JSON with a 200 OK.
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    /**
     * @DeleteMapping connects HTTP DELETE requests to this method.
     * Like getting a single student, it expects an ID in the URL.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long studentId) {
        // Call the service to permanently remove the database row.
        studentService.deleteStudentById(studentId);
        // Return a simple text completion message with a 200 OK.
        return new ResponseEntity<>("Student deleted successfully!", HttpStatus.OK);
    }
}
