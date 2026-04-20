package com.example.wishcycle.controller;

import com.example.wishcycle.model.Member;
import com.example.wishcycle.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishcycle")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        Member member = new Member();
        model.addAttribute("member", member);
        return "loginpage";
    }

    @PostMapping("/login/save")
    public String saveUser(@ModelAttribute Member member, HttpSession session) {
        Member validMember = memberService.createMember(member);
        if (validMember == null){
            return "loginpage";
        }

        session.setAttribute("member", validMember);

        return "redirect:/wishcycle/homepage";
    }

    @GetMapping("/about-us")
    public String getAboutUsPage() {
        return "about-us";
    }

    @GetMapping("/login/profile")
    public String getProfilePage(Model model, @PathVariable Long memberId) {
        Member member = memberService.getMemberById(memberId);
        model.addAttribute("memberProfile", member);
        return "user-profile";
    }
}
