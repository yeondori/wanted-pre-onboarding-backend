package com.wanted.assignment.service;

import com.wanted.assignment.domain.Company;
import com.wanted.assignment.domain.JobPosting;
import com.wanted.assignment.domain.Member;
import com.wanted.assignment.repository.CompanyRepository;
import com.wanted.assignment.repository.MemberRepository;
import com.wanted.assignment.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.wanted.assignment.service.ApplyStatus.*;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final CompanyRepository companyRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public PostService(PostRepository postRepository, CompanyRepository companyRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.companyRepository = companyRepository;
        this.memberRepository = memberRepository;
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

    public void updatePost(JobPosting updatedJobPosting) {

        Optional<JobPosting> targetJobPosting = postRepository.findById(updatedJobPosting.getId());

        if (targetJobPosting.isPresent()) {
            Company company = targetJobPosting.get().getCompany();
            updatedJobPosting.setCompany(company);
        }
        postRepository.save(updatedJobPosting);
    }

    public Long findCompanyIdById(Long id) {
        Optional<JobPosting> jobPosting = findById(id);
        if (!jobPosting.isPresent()) {
            throw new EntityNotFoundException("Job Posting not found for id: " + id);
        }
        return jobPosting.get().getCompany().getId();
    }

    public List<JobPosting> findByCompanyId(Long companyId) {return postRepository.findByCompanyId(companyId);}

    public List<Long> getJobPostingIdList(Long companyId) {return companyRepository.getJobPostingIdList(companyId);}

    public List<JobPostingDTO> mapToJobPostingDTOList(List<JobPosting> jobPostings) {
        List<JobPostingDTO> jobPostingDTOs = new ArrayList<>();

        for (JobPosting jobPosting : jobPostings) {
            JobPostingDTO dto = new JobPostingDTO();
            dto.setId(jobPosting.getId());
            dto.setCompanyName(jobPosting.getCompany().getName());
            dto.setCompanyCountry(jobPosting.getCompany().getCountry());
            dto.setCompanyRegion(jobPosting.getCompany().getRegion());
            dto.setPosition(jobPosting.getPosition());
            dto.setCompensation(jobPosting.getCompensation());
            dto.setTechStack(jobPosting.getTechStack());

            jobPostingDTOs.add(dto);
        }

        return jobPostingDTOs;
    }

    public ApplyStatus apply(Long postId, Long memberId) {

        Optional<Member> member = memberRepository.findById(memberId);
        Optional<JobPosting> selectedPost = postRepository.findById(postId);

        if (member.isEmpty()) return MEMBER_NOT_FOUND;
        if (selectedPost.isEmpty()) return POST_NOT_FOUND;
        if (member.get().getJobPosting() != null)return ALREADY_APPLIED;

        member.get().setJobPosting(selectedPost.get());
        memberRepository.save(member.get());
        postRepository.save(selectedPost.get());

        return SUCCESS;
    }
}
