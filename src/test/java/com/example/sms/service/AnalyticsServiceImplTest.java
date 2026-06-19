package com.example.sms.service;

import com.example.sms.dto.AnalyticsDto;
import com.example.sms.repository.StudentRepository;
import com.example.sms.service.impl.AnalyticsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnalyticsServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private AnalyticsServiceImpl analyticsService;

    @Test
    void testGetDashboardAnalytics() {
        // Arrange
        AnalyticsDto mockDto = new AnalyticsDto(2L, 3.75, 97.5, 90.0, 90.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        when(studentRepository.getDashboardAnalytics()).thenReturn(mockDto);

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
        AnalyticsDto mockDto = new AnalyticsDto(0L, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        when(studentRepository.getDashboardAnalytics()).thenReturn(mockDto);

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
