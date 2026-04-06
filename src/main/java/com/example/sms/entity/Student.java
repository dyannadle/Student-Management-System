package com.example.sms.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;

/**
 * The @Entity annotation tells Spring Data JPA that this Java class 
 * maps directly to a table in our MySQL database.
 */
@Entity
/**
 * The @Table annotation allows us to explicitly name our table. 
 * If we didn't include this, the table would just be named "student" by default.
 */
@Table(name = "students")
public class Student {

    /**
     * @Id marks this field as the Primary Key for the table.
     */
    @Id
    /**
     * @GeneratedValue tells MySQL to automatically increment the ID 
     * (1, 2, 3...) every time a new student is saved.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // By default, this creates a VARCHAR column named 'name'
    @Column(nullable = false)
    private String name;

    // We can enforce uniqueness directly at the database level!
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate dob; // Date of Birth

    /**
     * @Transient is a very special annotation!
     * It tells Hibernate: "Do NOT create an 'age' column in the database."
     * Why? Because Age changes every year. It's bad practice to store it.
     * We calculate it dynamically on the fly based on the 'dob' whenever someone asks for it.
     */
    @Transient
    private Integer age;

    // =========================================
    // CONSTRUCTORS
    // =========================================
    
    // 1. A No-Argument constructor is REQUIRED by Hibernate to function behind the scenes.
    public Student() {
    }

    // 2. A constructor WITHOUT the 'id'. We use this when creating new students 
    // because the database generates the ID for us automatically.
    public Student(String name, String email, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    // =========================================
    // GETTERS & SETTERS
    // =========================================
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    // Custom Getter for Age: Automatically calculates the age!
    public Integer getAge() {
        if (this.dob != null) {
            return Period.between(this.dob, LocalDate.now()).getYears();
        }
        return 0;
    }

    public void setAge(Integer age) { this.age = age; } // Typically unused, but good practice
}
