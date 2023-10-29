package com.wanted.assignment.repository;

import com.wanted.assignment.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT p.id FROM JobPosting p where p.company.id = :companyId")
    public List<Long> getJobPostingIdList(@Param("companyId") Long companyId);
}
