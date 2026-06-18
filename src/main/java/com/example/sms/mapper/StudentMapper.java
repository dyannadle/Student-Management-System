package com.example.sms.mapper;

import com.example.sms.dto.StudentDto;
import com.example.sms.entity.Student;

public class StudentMapper {

    // Convert JPA Entity into StudentDto
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

    // Convert StudentDto into JPA Entity
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
