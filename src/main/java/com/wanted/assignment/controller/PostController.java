package com.wanted.assignment.controller;

import com.wanted.assignment.domain.JobPosting;
import com.wanted.assignment.service.JobPostingDTO;
import com.wanted.assignment.domain.Member;
import com.wanted.assignment.exception.UserNotFoundException;
import com.wanted.assignment.service.MemberService;
import com.wanted.assignment.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(value = "/jobpostings")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @Autowired
    public PostController(PostService postService, MemberService memberService) {
        this.postService = postService;
        this.memberService = memberService;
    }

    //채용 공고 목록
    @GetMapping("")
    public String retrieveAllPosts(Model model) {
        List<JobPosting> jobPostings = postService.findAll();
        List<JobPostingDTO> jobPostingDTOS = postService.mapToJobPostingDTOList(jobPostings);
        model.addAttribute("jobPostingDTOs", jobPostingDTOS);
        return "jobpostings/home";
    }

    // 채용공고 상세
    @GetMapping("/{id}")
    public String retrieveDetails(@PathVariable Long id, Model model) {
        Optional<JobPosting> selectedPost = postService.findById(id);
        if (!selectedPost.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s] is not found", id));
        }
        model.addAttribute("jobPosting", selectedPost.get());

        List<Long> jobPostingIdList = postService.getJobPostingIdList(selectedPost.get().getCompany().getId());
        jobPostingIdList.remove(id); // 현재 채용공고 ID를 삭제

        if (jobPostingIdList.isEmpty()) {
            model.addAttribute("anotherPosts", "None");
        } else {
            model.addAttribute("anotherPosts", jobPostingIdList);
        }

        return "jobpostings/postDetails";
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
                List<JobPostingDTO> searchResultsDTO = postService.mapToJobPostingDTOList(searchResults);
                model.addAttribute("searchResults", searchResultsDTO);

                return "jobpostings/searchResults"; // 검색 결과가 있는 경우에 대한 뷰
            }
        }
    }

    // 채용공고 지원

    @PostMapping("/{id}/apply")
    public String applyForJob(@PathVariable Long id, @RequestParam String memberId, Model model) {

        return "jobpostings/applyResult";
    }
}

