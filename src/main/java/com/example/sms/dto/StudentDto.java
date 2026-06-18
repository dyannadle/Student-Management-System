package com.example.sms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for the Student Entity.
 * 
 * What is a DTO?
 * A DTO is an object that is used to encapsulate data, and send it from one subsystem of an application to another.
 * In our case, we use it to pass data between the API (Controller) and the Service layer, without exposing our actual database Entity.
 * This prevents over-posting attacks and decouples our database schema from our API contract.
 */
public class StudentDto {

    // The unique identifier. We only expect this when updating or fetching a student.
    private Long id;

    // We moved the Validation annotations from Student.java to here!
    // @NotBlank ensures that the user cannot submit an empty or null name via the API.
    @NotBlank(message = "Name is mandatory")
    private String name;

    // @NotBlank ensures the email is not empty.
    @NotBlank(message = "Email is mandatory")
    // @Email checks if the string follows a valid email format before the API accepts the request.
    @Email(message = "Please provide a valid email address")
    private String email;

    // @Past ensures the user cannot submit a Date of Birth in the future via the API.
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    /**
     * This age field is not passed by the user during creation!
     * Instead, our Service/Mapper logic dynamically populates it when returning data to the frontend.
     */
    private Integer age;

    public StudentDto() {
    }

    public StudentDto(Long id, String name, String email, LocalDate dob, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
