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

    @GetMapping("/login-page")
    public String login(Model model) {
        Member member = new Member();
        model.addAttribute("member", member);
        // return login form
        return "login-page";
    }

    @GetMapping("/signup-page")
    public String signup(Model model) {
        Member member = new Member();
        model.addAttribute("member", member);
        return "signup-page";
    }

    @PostMapping("/signup/save")
    public String saveNewMember(@ModelAttribute Member member, HttpSession session, Model model) {
        memberService.createMember(member);
        session.setAttribute("member", member);
        return "redirect:/wishcycle/login/profile";
    }

    @PostMapping("/login/save")
    public String saveMember(@ModelAttribute Member member, HttpSession session, Model model) {
        System.out.println(member.getEmail());
        System.out.println(member.getPassword());
        Member validMember = memberService.validMemberCheck(member);


        if (validMember != null) {
            session.setAttribute("member", validMember);
            // Session timeout CONTAINER DEFAULT (15 min)
            return "redirect:/wishcycle/login/profile";
        } else {
            model.addAttribute("Error", "no member exists");
            return "signup-page";
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
