package com.example.wishcycle.controller;

import ch.qos.logback.core.model.Model;
import com.example.wishcycle.model.Member;
import com.example.wishcycle.model.WishList;
import com.example.wishcycle.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishcycle")
public class WishController {

    private final WishService WishService;

    public WishController(WishService WishService) {
        this.WishService = WishService;
    }

//    @GetMapping("/homepage")
//    public String homepage(Model model) {
//        WishList wishList = new WishList();
//        model.addAttribute("wishList", wishList);
//        return "homepage";
//    }





//    BRUGES NÅR VI VIL TJEKKE OM DE ER LOGGET IND, REDIRECTER TIL LOGIN SIDEN HVIS DE IKKE ER
//    HttpSession session = request.getSession(false);
//
//    if (session == null || session.getAttribute("member") == null) {
//        return redirect:/wishcycle/login;
//    }

}


