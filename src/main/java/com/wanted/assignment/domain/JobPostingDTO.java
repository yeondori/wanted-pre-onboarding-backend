package com.wanted.assignment.domain;

public class JobPostingDTO {
    private Long id;
    private String companyName;
    private String companyCountry;
    private String companyRegion;
    private String jobPosition;
    private Long recruitmentCompensation;
    private String technologyUsed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    public String getCompanyRegion() {
        return companyRegion;
    }

    public void setCompanyRegion(String companyRegion) {
        this.companyRegion = companyRegion;
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

    public String getTechnologyUsed() {
        return technologyUsed;
    }

    public void setTechnologyUsed(String technologyUsed) {
        this.technologyUsed = technologyUsed;
    }
}
