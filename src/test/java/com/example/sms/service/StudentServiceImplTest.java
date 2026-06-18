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

/**
 * Unit tests for the StudentService layer.
 * 
 * In Unit Testing, we want to isolate the class we are testing. 
 * We do NOT want to connect to a real MySQL database. That would be an "Integration Test".
 * 
 * @ExtendWith(MockitoExtension.class) tells JUnit 5 to enable Mockito.
 * Mockito allows us to create "fake" versions (Mocks) of our dependencies (like the Repository) 
 * so we can control exactly what they return without needing a real database!
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    /**
     * @Mock creates a fake version of the StudentRepository.
     * It won't actually connect to a database. We will tell it exactly how to behave in each test.
     */
    @Mock
    private StudentRepository studentRepository;

    /**
     * @InjectMocks creates a real instance of our StudentServiceImpl and 
     * automatically injects the @Mock repository into it!
     */
    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private StudentDto studentDto;

    /**
     * @BeforeEach runs before EVERY single @Test method.
     * We use this to set up our test data so we don't have to rewrite it for every test.
     */
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

    /**
     * @Test marks this method as a JUnit test case.
     * A common pattern for tests is Arrange, Act, Assert (AAA).
     */
    @Test
    void testGetAllStudents() {
        // ARRANGE: Set up the mock's behavior. "When the service calls findAll(), return our fake list."
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));

        // ACT: Actually call the method we are testing.
        List<StudentDto> students = studentService.getAllStudents();

        // ASSERT: Verify the result is exactly what we expected.
        assertNotNull(students);
        assertEquals(1, students.size());
        assertEquals("Jane Doe", students.get(0).getName());
        // Verify that the mock repository was called exactly 1 time.
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
