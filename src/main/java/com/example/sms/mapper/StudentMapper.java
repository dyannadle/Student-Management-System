package com.example.sms.mapper;

import com.example.sms.dto.StudentDto;
import com.example.sms.dto.AcademicProfileDto;
import com.example.sms.entity.Student;
import com.example.sms.entity.AcademicProfile;

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
        
        AcademicProfileDto profileDto = null;
        if (student.getAcademicProfile() != null) {
            profileDto = new AcademicProfileDto();
            AcademicProfile p = student.getAcademicProfile();
            profileDto.setGpa(p.getGpa());
            profileDto.setAttendancePercentage(p.getAttendancePercentage());
            profileDto.setMathScore(p.getMathScore());
            profileDto.setScienceScore(p.getScienceScore());
            profileDto.setLiteratureScore(p.getLiteratureScore());
            profileDto.setHistoryScore(p.getHistoryScore());
            profileDto.setArtsScore(p.getArtsScore());
            profileDto.setTechnologyScore(p.getTechnologyScore());
            profileDto.setTerm1Score(p.getTerm1Score());
            profileDto.setTerm2Score(p.getTerm2Score());
            profileDto.setTerm3Score(p.getTerm3Score());
            profileDto.setTerm4Score(p.getTerm4Score());
            profileDto.setTerm5Score(p.getTerm5Score());
            profileDto.setTerm6Score(p.getTerm6Score());
        }
        
        return new StudentDto(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getDob(),
                student.getAge(), // The entity dynamically calculates this, and we bake it into the DTO!
                profileDto
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
        
        if (studentDto.getAcademicProfile() != null) {
            AcademicProfile profile = new AcademicProfile();
            AcademicProfileDto dto = studentDto.getAcademicProfile();
            profile.setGpa(dto.getGpa());
            profile.setAttendancePercentage(dto.getAttendancePercentage());
            profile.setMathScore(dto.getMathScore());
            profile.setScienceScore(dto.getScienceScore());
            profile.setLiteratureScore(dto.getLiteratureScore());
            profile.setHistoryScore(dto.getHistoryScore());
            profile.setArtsScore(dto.getArtsScore());
            profile.setTechnologyScore(dto.getTechnologyScore());
            profile.setTerm1Score(dto.getTerm1Score());
            profile.setTerm2Score(dto.getTerm2Score());
            profile.setTerm3Score(dto.getTerm3Score());
            profile.setTerm4Score(dto.getTerm4Score());
            profile.setTerm5Score(dto.getTerm5Score());
            profile.setTerm6Score(dto.getTerm6Score());
            
            // Set the bidirectional relationship
            profile.setStudent(student);
            student.setAcademicProfile(profile);
        }
        
        return student;
    }
}
