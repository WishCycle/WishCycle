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

    @GetMapping("/homepage")
    public String getHomepage() {
        return "homepage";
    }

    @GetMapping("/login")
    public String login(Model model) {
        Member member = new Member();
        model.addAttribute("member", member);
        // return login form
        return "login-page";
    }

    @PostMapping("/login/save")
    public String saveUser(@ModelAttribute Member member, HttpSession session, Model model) {
        Member validMember = memberService.createMember(member);

        if (validMember != null) {
            session.setAttribute("member", member);
            // Session timeout CONTAINER DEFAULT (15 min)
            return "redirect:/user-profile";
        } else {
            model.addAttribute("Error - no member exists");
            return "login-page";
        }
    }

    @GetMapping("/logout")  // Invalidate session and return landing page
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login-page";
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

    @PostMapping("/delete")
    public String deleteProfilePage(@ModelAttribute Member member) {
        memberService.deleteMember(member);
        return "redirect:/login-page";
    }
}
