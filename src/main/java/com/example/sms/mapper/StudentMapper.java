package com.example.sms.mapper;

import com.example.sms.dto.StudentDto;
import com.example.sms.entity.Student;

/**
 * Utility class to map between our internal Student Entity and the external StudentDto.
 * 
 * Why do we need this?
 * Entities shouldn't be exposed directly to the outside world. DTOs (Data Transfer Objects) are what the API uses.
 * This mapper acts as the translator between the database world (Entity) and the API world (DTO).
 */
public class StudentMapper {

    /**
     * Converts a JPA Entity (from the database) into a StudentDto (to be sent via API).
     * @param student The database entity
     * @return The DTO containing the safe, formatted data
     */
    public static StudentDto mapToStudentDto(Student student) {
        if (student == null) {
            return null;
        }
        
        return new StudentDto(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getDob(),
                student.getAge() // The entity dynamically calculates this, and we bake it into the DTO!
        );
    }

    /**
     * Converts a StudentDto (received via API) into a JPA Entity (to be saved in the database).
     * @param studentDto The incoming payload from the user
     * @return The newly created Entity
     */
    public static Student mapToStudent(StudentDto studentDto) {
        if (studentDto == null) {
            return null;
        }

        Student student = new Student();
        // ID should only be set if it exists (e.g. during an update)
        if (studentDto.getId() != null) {
            student.setId(studentDto.getId());
        }
        student.setName(studentDto.getName());
        student.setEmail(studentDto.getEmail());
        student.setDob(studentDto.getDob());
        // We explicitly DO NOT set Age on the entity, because Age is transient and shouldn't be saved!
        
        return student;
    }
}
