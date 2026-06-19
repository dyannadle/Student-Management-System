package com.example.sms.controller;

import com.example.sms.dto.StudentDto;
import com.example.sms.service.StudentService;
import com.example.sms.security.CustomUserDetailsService;
import com.example.sms.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the StudentController layer.
 * 
 * @WebMvcTest(StudentController.class) tells Spring Boot to load ONLY the web layer (the Controller).
 * It will not load the Service or Database layers, which makes the tests run much faster!
 */
@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerTest {

    /**
     * MockMvc allows us to send fake HTTP requests (GET, POST) to our Controller
     * without actually starting a real web server.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * @MockBean creates a fake version of the StudentService and places it into the Spring Application Context.
     * This ensures the Controller has a service to talk to, but we get to control what the service returns.
     */
    @MockBean
    private StudentService studentService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    private StudentDto studentDto;
    
    // ObjectMapper is used to convert Java objects into JSON strings and vice-versa.
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        studentDto = new StudentDto();
        studentDto.setId(1L);
        studentDto.setName("Jane Doe");
        studentDto.setEmail("jane@example.com");
        studentDto.setDob(LocalDate.of(2000, 1, 1));
        studentDto.setAge(24); // Assume age logic returns 24

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Test a GET request to retrieve all students.
     */
    @Test
    void testGetAllStudents() throws Exception {
        // ARRANGE
        when(studentService.getAllStudents(any(Pageable.class))).thenReturn(new PageImpl<>(Arrays.asList(studentDto)));

        // ACT & ASSERT: Perform the GET request and check the expectations
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk()) // Expect a 200 OK status
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Expect JSON format
                .andExpect(jsonPath("$.content[0].name").value("Jane Doe")) // Check the JSON payload value
                .andExpect(jsonPath("$.content[0].email").value("jane@example.com"));
    }

    @Test
    void testGetStudentById() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(studentDto);

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Jane Doe"));
    }

    @Test
    void testRegisterNewStudent_Success() throws Exception {
        when(studentService.saveStudent(any(StudentDto.class))).thenReturn(studentDto);

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jane Doe"));
    }

    @Test
    void testRegisterNewStudent_ValidationError() throws Exception {
        // Create an invalid DTO (blank name, invalid email)
        StudentDto invalidDto = new StudentDto();
        invalidDto.setName("");
        invalidDto.setEmail("invalid-email");

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateStudent() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(studentDto);
        when(studentService.updateStudent(any(StudentDto.class))).thenReturn(studentDto);

        mockMvc.perform(put("/api/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"));
    }

    @Test
    void testDeleteStudent() throws Exception {
        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Student deleted successfully!"));
    }
}
