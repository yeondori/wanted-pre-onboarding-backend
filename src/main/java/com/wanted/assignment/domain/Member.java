package com.wanted.assignment.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;

    @ManyToOne
    private JobPosting jobPosting;

    public void setJobPosting(JobPosting appliedPosting) {
        this.jobPosting = appliedPosting;
        appliedPosting.getApplicants().add(this);
    }
}
