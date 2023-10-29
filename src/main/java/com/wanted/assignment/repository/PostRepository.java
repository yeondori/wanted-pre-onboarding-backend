package com.wanted.assignment.repository;

import com.wanted.assignment.domain.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


public interface PostRepository extends JpaRepository<JobPosting, Long> {


    @Query("SELECT p FROM JobPosting p " +
            "WHERE p.company.name LIKE %:keyword% " +
            "OR p.company.country LIKE %:keyword% " +
            "OR p.company.region LIKE %:keyword% " +
            "OR p.position LIKE %:keyword% " +
            "OR p.details LIKE %:keyword% " +
            "OR p.techStack LIKE %:keyword% " +
            "OR CAST(p.compensation AS string) LIKE %:keyword%")
    List<JobPosting> findBySearchKeyword(@Param("keyword") String keyword);

    List<JobPosting> findByCompanyId(Long companyId);
}
