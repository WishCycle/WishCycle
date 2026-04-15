package com.example.wishcycle.controller;

import com.example.wishcycle.model.Member;
import com.example.wishcycle.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishcycle")
public class MemberController {

    private final WishService service;

    public MemberController(WishService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String login() {
        return "loginpage";
    }

//    @PostMapping("/login/save")
//    public String saveUser(Member member) {
//
//    }
}
