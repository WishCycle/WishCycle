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

    @GetMapping({"", "/", "/index"})
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/homepage")
    public String getHomepage(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("member");
        model.addAttribute("memberId", member.getMemberId());
        model.addAttribute("member", member);
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
    public String saveNewMember(@ModelAttribute Member member, HttpSession session) {
        memberService.createMember(member);
        Member savedMember = memberService.getMemberByEmail(member.getEmail());
        session.setAttribute("member", savedMember);
        return "redirect:/wishcycle/homepage";
    }

    @PostMapping("/login/save")
    public String saveMember(@ModelAttribute Member member, HttpSession session, Model model) {
        try {
            Member validMember = memberService.validMemberCheck(member);
            session.setAttribute("member", validMember);

            return "redirect:/wishcycle/homepage";
        } catch (Exception e) {
            // If password/email is wrong, send them back to login with an error message
            model.addAttribute("error", "Invalid credentials. Please try again.");
            return "login-page";
        }
    }

    @GetMapping("/logout")  // Invalidate session and return landing page
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/wishcycle/index";
    }

    @GetMapping("/about-us")
    public String getAboutUsPage(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            model.addAttribute("memberId", member.getMemberId());
        }
        return "about-us";
    }

    @GetMapping("/login/profile/{memberId}")
    public String getProfilePage(@PathVariable Long memberId, Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        if (member == null || !member.getMemberId().equals(memberId)) {
            return "redirect:/wishcycle/login-page";
        }

        model.addAttribute("member", member);
        model.addAttribute("memberId", member.getMemberId());
        return "user-profile";
    }

    @PostMapping("/login/profile/{memberId}/delete")
    public String deleteProfilePage(Model model, HttpSession session, @PathVariable Long memberId) {
        Member member = (Member) session.getAttribute("member");
        model.addAttribute("member", member);
        memberService.deleteMember(member);
        return "redirect:/wishcycle/homepage";
    }


}
