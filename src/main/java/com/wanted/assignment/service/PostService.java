package com.wanted.assignment.service;

import com.wanted.assignment.domain.Company;
import com.wanted.assignment.domain.JobPosting;
import com.wanted.assignment.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void save(JobPosting jobPosting) {
        postRepository.save(jobPosting);
    }

    public List<JobPosting> findAll() { //전체 사용자 조회
        return postRepository.findAll();
    }

    public Optional<JobPosting> findById(Long id) {
        return postRepository.findById(id);
    }
    public void deleteById(Long id) {postRepository.deleteById(id);}

    public List<JobPosting> findBySearchKeyword(String keyword) { return postRepository.findBySearchKeyword(keyword);}

    public JobPosting updatePost(JobPosting updatedJobPosting) {

        Optional<JobPosting> targetJobPosting = postRepository.findById(updatedJobPosting.getId());

        if (targetJobPosting.isPresent()) {
            Company company = targetJobPosting.get().getCompany();
            updatedJobPosting.setCompany(company);
        }
        return postRepository.save(updatedJobPosting);
    }

    public Long findCompanyIdById(Long id) {
        Optional<JobPosting> jobPosting = findById(id);
        if (!jobPosting.isPresent()) {
            throw new EntityNotFoundException("Job Posting not found for id: " + id);
        }
        return jobPosting.get().getCompany().getId();
    }

    public List<JobPosting> findByCompanyId(Long companyId) {return postRepository.findByCompanyId(companyId);}
}
