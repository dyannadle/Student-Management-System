package com.example.sms.service.impl;

import com.example.sms.dto.AnalyticsDto;
import com.example.sms.repository.StudentRepository;
import com.example.sms.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final StudentRepository studentRepository;

    @Autowired
    public AnalyticsServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Cacheable("dashboardAnalytics")
    public AnalyticsDto getDashboardAnalytics() {
        return studentRepository.getDashboardAnalytics();
    }
}
