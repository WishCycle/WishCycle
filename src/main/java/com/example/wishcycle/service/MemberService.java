package com.example.wishcycle.service;

import com.example.wishcycle.model.Member;
import com.example.wishcycle.repository.jdbc.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getMemberById(int id) {
        Member member = memberRepository.getMemberById(id);

        if (member != null & member.getMemberId() == id) {
            return member;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
    }

    public void createMember(Member member) {

    }



}
