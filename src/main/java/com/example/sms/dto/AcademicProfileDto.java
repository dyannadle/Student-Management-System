package com.example.sms.dto;

public class AcademicProfileDto {

    private Double gpa = 0.0;
    private Double attendancePercentage = 0.0;

    private Integer mathScore = 0;
    private Integer scienceScore = 0;
    private Integer literatureScore = 0;
    private Integer historyScore = 0;
    private Integer artsScore = 0;
    private Integer technologyScore = 0;

    private Integer term1Score = 0;
    private Integer term2Score = 0;
    private Integer term3Score = 0;
    private Integer term4Score = 0;
    private Integer term5Score = 0;
    private Integer term6Score = 0;

    public AcademicProfileDto() {}

    // Getters and Setters
    public Double getGpa() { return gpa; }
    public void setGpa(Double gpa) { this.gpa = gpa; }

    public Double getAttendancePercentage() { return attendancePercentage; }
    public void setAttendancePercentage(Double attendancePercentage) { this.attendancePercentage = attendancePercentage; }

    public Integer getMathScore() { return mathScore; }
    public void setMathScore(Integer mathScore) { this.mathScore = mathScore; }

    public Integer getScienceScore() { return scienceScore; }
    public void setScienceScore(Integer scienceScore) { this.scienceScore = scienceScore; }

    public Integer getLiteratureScore() { return literatureScore; }
    public void setLiteratureScore(Integer literatureScore) { this.literatureScore = literatureScore; }

    public Integer getHistoryScore() { return historyScore; }
    public void setHistoryScore(Integer historyScore) { this.historyScore = historyScore; }

    public Integer getArtsScore() { return artsScore; }
    public void setArtsScore(Integer artsScore) { this.artsScore = artsScore; }

    public Integer getTechnologyScore() { return technologyScore; }
    public void setTechnologyScore(Integer technologyScore) { this.technologyScore = technologyScore; }

    public Integer getTerm1Score() { return term1Score; }
    public void setTerm1Score(Integer term1Score) { this.term1Score = term1Score; }

    public Integer getTerm2Score() { return term2Score; }
    public void setTerm2Score(Integer term2Score) { this.term2Score = term2Score; }

    public Integer getTerm3Score() { return term3Score; }
    public void setTerm3Score(Integer term3Score) { this.term3Score = term3Score; }

    public Integer getTerm4Score() { return term4Score; }
    public void setTerm4Score(Integer term4Score) { this.term4Score = term4Score; }

    public Integer getTerm5Score() { return term5Score; }
    public void setTerm5Score(Integer term5Score) { this.term5Score = term5Score; }

    public Integer getTerm6Score() { return term6Score; }
    public void setTerm6Score(Integer term6Score) { this.term6Score = term6Score; }
}
