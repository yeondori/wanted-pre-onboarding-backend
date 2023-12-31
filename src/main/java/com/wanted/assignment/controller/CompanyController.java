package com.wanted.assignment.controller;

import com.wanted.assignment.domain.Company;
import com.wanted.assignment.domain.JobPosting;
import com.wanted.assignment.service.CompanyService;
import com.wanted.assignment.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/companies")
public class CompanyController {

    private final PostService postService;
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService, PostService postService) {
        this.companyService = companyService;
        this.postService = postService;
    }
    //기업 홈화면
    @GetMapping("")
    public String home() {
        return "companies/companiesHome";
    }

    //기업 회원 페이지
    @GetMapping("/{companyId}")
    public String getJobPostingsByCompany(@PathVariable Long companyId, Model model) {
        Optional<Company> company = companyService.findById(companyId);

        if (!company.isPresent()) {
            return "Company ID가 존재하지 않습니다.";
        }

        List<JobPosting> jobPostings = postService.findByCompanyId(company.get().getId());

        model.addAttribute("jobPostings", jobPostings);
        model.addAttribute("company", company.get());

        return "companies/postList";
    }

    //채용공고 등록 폼
    @GetMapping("/{companyId}/new")
    public String showJobPostingForm(@PathVariable Long companyId, Model model) {
        model.addAttribute("companyId", companyId);
        return "companies/createdPostForm";
    }

    //채용공고 등록
    @PostMapping("/{companyId}/new")
    public String create(PostForm form) {
        Company company = companyService.findById(form.getCompanyId()).orElse(null);

        if (company == null) {
            return "Company ID가 존재하지 않습니다.";
        }
        Long companyId = form.getCompanyId();

        JobPosting newPost = new JobPosting();

        newPost.setCompany(company);
        newPost.setPosition(form.getJobPosition());
        newPost.setDetails(form.getRecruitmentDetails());
        newPost.setTechStack(form.getTechnologyUsed());
        newPost.setCompensation(form.getRecruitmentCompensation());

        postService.save(newPost);

        return "redirect:/companies/" + companyId;
    }

    //채용공고 삭제
    @DeleteMapping("/deleteJobPosting/{postId}")
    public String deleteJobPosting(@PathVariable Long postId) {
        Long companyId = postService.findCompanyIdById(postId);
        postService.deleteById(postId);
        return "redirect:/companies/" + companyId;
    }

    //채용공고 수정
    @GetMapping("/editJobPosting/{jobPostingId}")
    public String getEditJobPostingForm(@PathVariable Long jobPostingId, Model model) {
        Optional<JobPosting> jobPosting = postService.findById(jobPostingId);

        if (!jobPosting.isPresent()) {
            return "채용 공고를 찾을 수 없습니다.";
        }

        model.addAttribute("jobPosting", jobPosting.get());

        return "companies/editJobPosting";
    }

    //채용공고 수정결과 업데이트
    @PostMapping("/updateJobPosting/{jobPostingId}")
    public String updateJobPosting(@PathVariable Long jobPostingId, @ModelAttribute JobPosting updatedJobPosting) {
        postService.updatePost(updatedJobPosting);
        Long companyId = updatedJobPosting.getCompany().getId();
        return "redirect:/companies/" + companyId;
    }
}
