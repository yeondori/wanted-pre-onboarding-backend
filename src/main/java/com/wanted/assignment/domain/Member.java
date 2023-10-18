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
    private JobPosting appliedPosting;

    public void setAppliedPosting(JobPosting appliedPosting) {
        this.appliedPosting = appliedPosting;
        appliedPosting.getApplicantList().add(this);
    }
}
