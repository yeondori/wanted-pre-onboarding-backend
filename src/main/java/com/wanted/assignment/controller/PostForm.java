package com.wanted.assignment.controller;

public class PostForm {

    private Long companyId;
    private String jobPosition;
    private Long recruitmentCompensation;
    private String recruitmentDetails;
    private String technologyUsed;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public Long getRecruitmentCompensation() {
        return recruitmentCompensation;
    }

    public void setRecruitmentCompensation(Long recruitmentCompensation) {
        this.recruitmentCompensation = recruitmentCompensation;
    }

    public String getRecruitmentDetails() {
        return recruitmentDetails;
    }

    public void setRecruitmentDetails(String recruitmentDetails) {
        this.recruitmentDetails = recruitmentDetails;
    }

    public String getTechnologyUsed() {
        return technologyUsed;
    }

    public void setTechnologyUsed(String technologyUsed) {
        this.technologyUsed = technologyUsed;
    }
}
