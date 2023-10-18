package com.wanted.assignment.service;

import com.wanted.assignment.domain.Company;
import com.wanted.assignment.domain.Member;
import com.wanted.assignment.repository.CompanyRepository;
import com.wanted.assignment.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public Optional<Member> findById(Long memberId) { return memberRepository.findById(memberId);}
}
