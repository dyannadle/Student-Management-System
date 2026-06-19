package com.example.sms.service;

import com.example.sms.dto.AnalyticsDto;
import com.example.sms.entity.AcademicProfile;
import com.example.sms.entity.Student;
import com.example.sms.repository.StudentRepository;
import com.example.sms.service.impl.AnalyticsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnalyticsServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private AnalyticsServiceImpl analyticsService;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        AcademicProfile profile1 = new AcademicProfile();
        profile1.setGpa(3.5);
        profile1.setAttendancePercentage(95.0);
        profile1.setMathScore(85);
        profile1.setScienceScore(90);

        student1 = new Student();
        student1.setAcademicProfile(profile1);

        AcademicProfile profile2 = new AcademicProfile();
        profile2.setGpa(4.0);
        profile2.setAttendancePercentage(100.0);
        profile2.setMathScore(95);
        profile2.setScienceScore(90);

        student2 = new Student();
        student2.setAcademicProfile(profile2);
    }

    @Test
    void testGetDashboardAnalytics() {
        // Arrange
        List<Student> students = Arrays.asList(student1, student2);
        when(studentRepository.findAll()).thenReturn(students);

        // Act
        AnalyticsDto analytics = analyticsService.getDashboardAnalytics();

        // Assert
        assertNotNull(analytics);
        assertEquals(2L, analytics.getTotalStudents());
        assertEquals(3.75, analytics.getAverageGpa());
        assertEquals(97.5, analytics.getAverageAttendance());
        assertEquals(90.0, analytics.getMathAvg());
        assertEquals(90.0, analytics.getScienceAvg());
    }

    @Test
    void testGetDashboardAnalytics_EmptyDatabase() {
        // Arrange
        when(studentRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        AnalyticsDto analytics = analyticsService.getDashboardAnalytics();

        // Assert
        assertNotNull(analytics);
        assertEquals(0L, analytics.getTotalStudents());
        assertEquals(0.0, analytics.getAverageGpa());
        assertEquals(0.0, analytics.getAverageAttendance());
        assertEquals(0.0, analytics.getMathAvg());
        assertEquals(0.0, analytics.getScienceAvg());
    }
}
