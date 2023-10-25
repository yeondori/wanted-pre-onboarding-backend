package com.wanted.assignment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPosting {

    @Id @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID")
    @JsonIgnoreProperties({"id"})
    private Company company;

    private String position;
    private Long compensation;
    private String details;
    private String techStack;

    @OneToMany(mappedBy = "appliedPosting", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Member> applicantList = new ArrayList<>();

}
