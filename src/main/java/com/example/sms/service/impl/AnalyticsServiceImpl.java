package com.example.sms.service.impl;

import com.example.sms.dto.AnalyticsDto;
import com.example.sms.entity.AcademicProfile;
import com.example.sms.entity.Student;
import com.example.sms.repository.StudentRepository;
import com.example.sms.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final StudentRepository studentRepository;

    @Autowired
    public AnalyticsServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public AnalyticsDto getDashboardAnalytics() {
        List<Student> students = studentRepository.findAll();
        
        AnalyticsDto analytics = new AnalyticsDto();
        analytics.setTotalStudents(students.size());
        
        if (students.isEmpty()) {
            return analytics;
        }

        double totalGpa = 0;
        double totalAttendance = 0;
        double totalMath = 0, totalScience = 0, totalLit = 0, totalHist = 0, totalArts = 0, totalTech = 0;
        double totalT1 = 0, totalT2 = 0, totalT3 = 0, totalT4 = 0, totalT5 = 0, totalT6 = 0;
        
        int validProfiles = 0;

        for (Student student : students) {
            AcademicProfile profile = student.getAcademicProfile();
            if (profile != null) {
                validProfiles++;
                totalGpa += profile.getGpa() != null ? profile.getGpa() : 0.0;
                totalAttendance += profile.getAttendancePercentage() != null ? profile.getAttendancePercentage() : 0.0;
                
                totalMath += profile.getMathScore() != null ? profile.getMathScore() : 0;
                totalScience += profile.getScienceScore() != null ? profile.getScienceScore() : 0;
                totalLit += profile.getLiteratureScore() != null ? profile.getLiteratureScore() : 0;
                totalHist += profile.getHistoryScore() != null ? profile.getHistoryScore() : 0;
                totalArts += profile.getArtsScore() != null ? profile.getArtsScore() : 0;
                totalTech += profile.getTechnologyScore() != null ? profile.getTechnologyScore() : 0;
                
                totalT1 += profile.getTerm1Score() != null ? profile.getTerm1Score() : 0;
                totalT2 += profile.getTerm2Score() != null ? profile.getTerm2Score() : 0;
                totalT3 += profile.getTerm3Score() != null ? profile.getTerm3Score() : 0;
                totalT4 += profile.getTerm4Score() != null ? profile.getTerm4Score() : 0;
                totalT5 += profile.getTerm5Score() != null ? profile.getTerm5Score() : 0;
                totalT6 += profile.getTerm6Score() != null ? profile.getTerm6Score() : 0;
            }
        }

        if (validProfiles > 0) {
            analytics.setAverageGpa(totalGpa / validProfiles);
            analytics.setAverageAttendance(totalAttendance / validProfiles);
            
            analytics.setMathAvg(totalMath / validProfiles);
            analytics.setScienceAvg(totalScience / validProfiles);
            analytics.setLiteratureAvg(totalLit / validProfiles);
            analytics.setHistoryAvg(totalHist / validProfiles);
            analytics.setArtsAvg(totalArts / validProfiles);
            analytics.setTechnologyAvg(totalTech / validProfiles);
            
            analytics.setTerm1Avg(totalT1 / validProfiles);
            analytics.setTerm2Avg(totalT2 / validProfiles);
            analytics.setTerm3Avg(totalT3 / validProfiles);
            analytics.setTerm4Avg(totalT4 / validProfiles);
            analytics.setTerm5Avg(totalT5 / validProfiles);
            analytics.setTerm6Avg(totalT6 / validProfiles);
        }

        return analytics;
    }
}
