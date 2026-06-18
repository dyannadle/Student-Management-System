package com.example.sms.service;

import com.example.sms.dto.StudentDto;
import com.example.sms.entity.Student;
import com.example.sms.exception.ResourceNotFoundException;
import com.example.sms.repository.StudentRepository;
import com.example.sms.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private StudentDto studentDto;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("Jane Doe");
        student.setEmail("jane@example.com");
        student.setDob(LocalDate.of(2000, 1, 1));

        studentDto = new StudentDto();
        studentDto.setId(1L);
        studentDto.setName("Jane Doe");
        studentDto.setEmail("jane@example.com");
        studentDto.setDob(LocalDate.of(2000, 1, 1));
    }

    @Test
    void testGetAllStudents() {
        // Arrange
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));

        // Act
        List<StudentDto> students = studentService.getAllStudents();

        // Assert
        assertNotNull(students);
        assertEquals(1, students.size());
        assertEquals("Jane Doe", students.get(0).getName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testSaveStudent() {
        // Arrange
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Act
        StudentDto savedStudent = studentService.saveStudent(studentDto);

        // Assert
        assertNotNull(savedStudent);
        assertEquals(1L, savedStudent.getId());
        assertEquals("Jane Doe", savedStudent.getName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testGetStudentById_Success() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // Act
        StudentDto foundStudent = studentService.getStudentById(1L);

        // Assert
        assertNotNull(foundStudent);
        assertEquals("Jane Doe", foundStudent.getName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStudentById_NotFound() {
        // Arrange
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.getStudentById(2L);
        });
        verify(studentRepository, times(1)).findById(2L);
    }

    @Test
    void testUpdateStudent() {
        // Arrange
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Act
        studentDto.setName("Jane Updated");
        StudentDto updatedStudent = studentService.updateStudent(studentDto);

        // Assert
        assertNotNull(updatedStudent);
        // The mock returns 'student' whose name is "Jane Doe", so the mapper will map "Jane Doe"
        assertEquals("Jane Doe", updatedStudent.getName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testDeleteStudentById() {
        // Arrange
        doNothing().when(studentRepository).deleteById(1L);

        // Act
        studentService.deleteStudentById(1L);

        // Assert
        verify(studentRepository, times(1)).deleteById(1L);
    }
}
