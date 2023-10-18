package com.wanted.assignment.controller;

import com.wanted.assignment.domain.JobPosting;
import com.wanted.assignment.domain.JobPostingDTO;
import com.wanted.assignment.domain.Member;
import com.wanted.assignment.exception.UserNotFoundException;
import com.wanted.assignment.service.MemberService;
import com.wanted.assignment.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(value = "/jobpostings")
public class PostController {

    private final PostService postService;
    private final MemberService memberService; // MemberService를 추가

    @Autowired
    public PostController(PostService postService, MemberService memberService) {
        this.postService = postService;
        this.memberService = memberService;
    }

    //채용 공고 목록
    @GetMapping("")
    public String retrieveAllPosts(Model model) {
        List<JobPosting> jobPostings = postService.findAll();
        List<JobPostingDTO> jobPostingDTOS = mapToJobPostingDTOList(jobPostings);
        model.addAttribute("jobPostingDTOs", jobPostingDTOS);
        return "jobpostings/home"; // Thymeleaf 템플릿 파일 이름
    }

    // 채용공고 상세
    @GetMapping("/{id}")
    public String retrieveDetails(@PathVariable Long id, Model model) {
        Optional<JobPosting> selectedPost = postService.findById(id);
        if (!selectedPost.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s] is not found", id));
        }
        model.addAttribute("jobPosting", selectedPost.get());

        List<Long> jobPostingIdList = selectedPost.get().getCompany().getJobPostingIdList();
        jobPostingIdList.remove(id); // 현재 채용공고 ID를 삭제

        if (jobPostingIdList.isEmpty()) {
            model.addAttribute("anotherPosts", "None");
        } else {
            model.addAttribute("anotherPosts", jobPostingIdList);
        }

        return "jobpostings/postDetails";
    }


    // 채용공고 지원
    public static final int SUCCESS = 1;
    public static final int MEMBER_NOT_FOUND = 2;
    public static final int POST_NOT_FOUND = 3;
    public static final int ALREADY_APPLIED = 4;

    @PostMapping("/{id}/apply")
    public String applyForJob(@PathVariable Long id, @RequestParam String memberId, Model model) {
        Long applicantId = Long.parseLong(memberId);
        Map<String, Object> response = new HashMap<>();
        int status = SUCCESS;

        if (memberService.findById(applicantId).isEmpty()) {
            status = MEMBER_NOT_FOUND;
        } else {
            Optional<JobPosting> selectedPost = postService.findById(id);
            if (!selectedPost.isPresent()) {
                status = POST_NOT_FOUND;
            } else {
                JobPosting jobPosting = selectedPost.get();
                Member applicant = memberService.findById(applicantId).get();
                if (applicant.getAppliedPosting() != null) {
                    status = ALREADY_APPLIED;
                } else {
                    applicant.setAppliedPosting(jobPosting);
                    memberService.save(applicant);
                    postService.save(jobPosting);
                }
            }
        }
        model.addAttribute("jobPostingId", id);
        model.addAttribute("applicantId", applicantId);
        model.addAttribute("applyStatus", status);
        return "jobpostings/applyResult";
    }



    // 채용공고 검색
    @GetMapping("/search")
    public String searchJobPostings(@RequestParam("keyword") String keyword, Model model) {
        if (keyword.isEmpty()) {
            return "redirect:/jobpostings";
        } else {
            List<JobPosting> searchResults = postService.findBySearchKeyword(keyword);
            model.addAttribute("keyword", keyword);

            if (searchResults.isEmpty()) {
                return "jobpostings/noResults"; // 검색 결과가 없는 경우에 대한 뷰
            } else {
                List<JobPostingDTO> searchResultsDTO = mapToJobPostingDTOList(searchResults);
                model.addAttribute("searchResults", searchResultsDTO);

                return "jobpostings/searchResults"; // 검색 결과가 있는 경우에 대한 뷰
            }
        }
    }

    private List<JobPostingDTO> mapToJobPostingDTOList(List<JobPosting> jobPostings) {
        List<JobPostingDTO> jobPostingDTOs = new ArrayList<>();

        for (JobPosting jobPosting : jobPostings) {
            JobPostingDTO dto = new JobPostingDTO();
            dto.setId(jobPosting.getId());
            dto.setCompanyName(jobPosting.getCompany().getName());
            dto.setCompanyCountry(jobPosting.getCompany().getCountry());
            dto.setCompanyRegion(jobPosting.getCompany().getRegion());
            dto.setJobPosition(jobPosting.getJobPosition());
            dto.setRecruitmentCompensation(jobPosting.getRecruitmentCompensation());
            dto.setTechnologyUsed(jobPosting.getTechnologyUsed());

            jobPostingDTOs.add(dto);
        }

        return jobPostingDTOs;
    }
}

