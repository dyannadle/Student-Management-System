package com.example.sms.entity;

// Import JPA decorators (like @Entity, @Table, @Id). "jakarta.persistence" is the modern version of "javax.persistence".
import jakarta.persistence.*;
// Import Validation tools to enforce rules on our data.
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

// Import Java date and time libraries for managing the Date of Birth.
import java.time.LocalDate;
import java.time.Period;

/**
 * The @Entity annotation tells Spring Data JPA that this Java class 
 * maps directly to a table in our MySQL database. Every instance of this class is a row!
 */
@Entity
/**
 * The @Table annotation allows us to explicitly name our table. 
 * If we didn't include this, the table would just be named "student" by default.
 */
@Table(name = "students")
public class Student {

    /**
     * @Id marks this exact field as the "Primary Key" for the table. It must be unique.
     */
    @Id
    /**
     * @GeneratedValue tells MySQL to automatically increment the ID 
     * (1, 2, 3...) every time a new student is saved. We don't have to manage it!
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // The actual variable that holds the ID.
    private Long id;

    // @NotBlank ensures that the user cannot submit an empty or null name. 
    @NotBlank(message = "Name is mandatory")
    // @Column(nullable = false) tells the MySQL Database to set this column to "NOT NULL".
    @Column(nullable = false)
    // The actual variable holding the student's name. (MySQL will build a VARCHAR column).
    private String name;

    // We enforce that the email cannot be blank.
    @NotBlank(message = "Email is mandatory")
    // @Email checks if the string follows a valid email format (e.g. contains an '@').
    @Email(message = "Please provide a valid email address")
    // unique = true enforce a database-level restriction so we cannot save duplicate emails!
    @Column(nullable = false, unique = true)
    // The actual variable holding the student's email.
    private String email;

    // @Past ensures the user cannot submit a Date of Birth in the future!
    @Past(message = "Date of birth must be in the past")
    @Column(nullable = false)
    // Using LocalDate instead of the older java.util.Date is standard practice in modern Java.
    private LocalDate dob;

    /**
     * @Transient is a very special annotation!
     * It tells Hibernate: "Do NOT create an 'age' column in the database."
     * Why? Because Age changes every year. It's bad practice to store it permanently.
     * Instead, we calculate it dynamically on the fly whenever someone asks for it.
     */
    @Transient
    private Integer age;

    // =========================================
    // CONSTRUCTORS
    // =========================================
    
    // 1. A No-Argument constructor is absolutely REQUIRED by Hibernate to construct objects behind the scenes.
    public Student() {
    }

    // 2. A constructor WITHOUT the 'id' and 'age'. We use this when creating new students 
    // because the database generates the ID for us automatically, and the age is transient!
    public Student(String name, String email, LocalDate dob) {
        // "this." refers to the class variable, differentiating it from the method argument.
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    // =========================================
    // GETTERS & SETTERS (Standard Java logic!)
    // =========================================
    
    // Return the ID.
    public Long getId() { return id; }
    // Manually set an ID (Rarely used since MySQL does this automatically).
    public void setId(Long id) { this.id = id; }

    // Return the name.
    public String getName() { return name; }
    // Update the name.
    public void setName(String name) { this.name = name; }

    // Return the email.
    public String getEmail() { return email; }
    // Update the email.
    public void setEmail(String email) { this.email = email; }

    // Return the date of birth.
    public LocalDate getDob() { return dob; }
    // Update the date of birth.
    public void setDob(LocalDate dob) { this.dob = dob; }

    // Custom Getter for Age: Automatically calculates the age!
    public Integer getAge() {
        // Make sure dob is not null so we don't get an error.
        if (this.dob != null) {
            // Find the Period (time gap) between their DOB and today's current date, and return the Years!
            return Period.between(this.dob, LocalDate.now()).getYears();
        }
        // Fallback return.
        return 0;
    }

    // We keep the setter for completion, but since it's dynamic, we rarely manually call this.
    public void setAge(Integer age) { this.age = age; }
}
