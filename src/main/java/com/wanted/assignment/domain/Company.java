package com.wanted.assignment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id @GeneratedValue
    @Column(name = "COMPANY_ID")
    private Long id;

    private String name;
    private String country;
    private String region;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<JobPosting> jobPostings = new ArrayList<>();
}
