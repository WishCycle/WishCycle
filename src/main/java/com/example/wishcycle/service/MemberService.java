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

    public Member getMemberById(Long id) {
        Member member = memberRepository.getMemberById(id);

        assert member != null;
        if (member != null & member.getMemberId() == id) {
            return member;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
    }

    public Member getMemberByEmail(String email) {
        Member member = memberRepository.getMemberByEmail(email);
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
        }
        return member;
    }

    public void createMember(Member member) {
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login Failed");
        }
        if (memberRepository.getMemberByEmail(member.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }
        if (memberRepository.getMemberByUsername(member.getName()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already in use");
        }
        memberRepository.createMember(member);
    }

    public Member validMemberCheck(Member member) {
        if (memberRepository.getMemberByEmail(member.getEmail()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member not found");
        }
        Member checkMember = memberRepository.getMemberByEmail(member.getEmail());
                if (!checkMember.getPassword().trim().equals(member.getPassword().trim())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password does not match user found in database.");
        }
        return memberRepository.getMemberByEmail(member.getEmail());
    }

    public void deleteMember(Member member) {
        if (memberRepository.getMemberById(member.getMemberId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member not found");
        }
        memberRepository.deleteMember(member);
    }

    public void updateMember(Member member) {
        memberRepository.updateMember(member);
    }
}
