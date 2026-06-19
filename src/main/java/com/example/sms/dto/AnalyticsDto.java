package com.example.sms.dto;

import java.util.List;
import java.util.Map;

public class AnalyticsDto {
    private long totalStudents;
    private double averageGpa;
    private double averageAttendance;

    // Averages across all students for units
    private double mathAvg;
    private double scienceAvg;
    private double literatureAvg;
    private double historyAvg;
    private double artsAvg;
    private double technologyAvg;

    // Averages across all students for terms
    private double term1Avg;
    private double term2Avg;
    private double term3Avg;
    private double term4Avg;
    private double term5Avg;
    private double term6Avg;

    public AnalyticsDto() {}

    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(long totalStudents) { this.totalStudents = totalStudents; }

    public double getAverageGpa() { return averageGpa; }
    public void setAverageGpa(double averageGpa) { this.averageGpa = averageGpa; }

    public double getAverageAttendance() { return averageAttendance; }
    public void setAverageAttendance(double averageAttendance) { this.averageAttendance = averageAttendance; }

    public double getMathAvg() { return mathAvg; }
    public void setMathAvg(double mathAvg) { this.mathAvg = mathAvg; }

    public double getScienceAvg() { return scienceAvg; }
    public void setScienceAvg(double scienceAvg) { this.scienceAvg = scienceAvg; }

    public double getLiteratureAvg() { return literatureAvg; }
    public void setLiteratureAvg(double literatureAvg) { this.literatureAvg = literatureAvg; }

    public double getHistoryAvg() { return historyAvg; }
    public void setHistoryAvg(double historyAvg) { this.historyAvg = historyAvg; }

    public double getArtsAvg() { return artsAvg; }
    public void setArtsAvg(double artsAvg) { this.artsAvg = artsAvg; }

    public double getTechnologyAvg() { return technologyAvg; }
    public void setTechnologyAvg(double technologyAvg) { this.technologyAvg = technologyAvg; }

    public double getTerm1Avg() { return term1Avg; }
    public void setTerm1Avg(double term1Avg) { this.term1Avg = term1Avg; }

    public double getTerm2Avg() { return term2Avg; }
    public void setTerm2Avg(double term2Avg) { this.term2Avg = term2Avg; }

    public double getTerm3Avg() { return term3Avg; }
    public void setTerm3Avg(double term3Avg) { this.term3Avg = term3Avg; }

    public double getTerm4Avg() { return term4Avg; }
    public void setTerm4Avg(double term4Avg) { this.term4Avg = term4Avg; }

    public double getTerm5Avg() { return term5Avg; }
    public void setTerm5Avg(double term5Avg) { this.term5Avg = term5Avg; }

    public double getTerm6Avg() { return term6Avg; }
    public void setTerm6Avg(double term6Avg) { this.term6Avg = term6Avg; }
}
