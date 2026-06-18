package com.example.sms.controller;

import com.example.sms.dto.StudentDto;
import com.example.sms.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private StudentDto studentDto;
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

    @Test
    void testGetAllStudents() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Arrays.asList(studentDto));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Jane Doe"))
                .andExpect(jsonPath("$[0].email").value("jane@example.com"));
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
