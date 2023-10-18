package com.wanted.assignment.service;

import com.wanted.assignment.domain.Company;
import com.wanted.assignment.domain.JobPosting;
import com.wanted.assignment.repository.CompanyRepository;
import com.wanted.assignment.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void save(Company company) {
        companyRepository.save(company);
    }

    public Optional<Company> findById(Long companyId) { return companyRepository.findById(companyId);}

    public List<Long> getJobPostingsForCompany(Long companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company != null) {
            return company.getJobPostingIdList();
        }
        return Collections.emptyList();
    }
}
