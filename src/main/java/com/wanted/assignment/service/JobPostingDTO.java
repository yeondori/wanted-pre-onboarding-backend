package com.wanted.assignment.service;

import lombok.Data;

@Data
public class JobPostingDTO {
    private Long id;
    private String companyName;
    private String companyCountry;
    private String companyRegion;
    private String position;
    private Long compensation;
    private String techStack;
}
