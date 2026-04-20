package com.example.wishcycle.controller;

import com.example.wishcycle.model.Member;
import com.example.wishcycle.model.WishList;
import com.example.wishcycle.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/wishcycle")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping("/homepage")
    public String homepage(Model model) {
        WishList wishList = new WishList();
        model.addAttribute("wishList", wishList);
        return "homepage";
    }

    @GetMapping("/wishlists")
    public String getWishLists(Model model) {
        List<WishList> wishLists = wishService.getWishLists();
        model.addAttribute("wishlists", wishLists);
        return "";
    }

    @GetMapping("/wishlists/{memberId}")
    public String getWishListsByMemberId(Model model, @PathVariable Long memberId) {
        List<WishList> wishLists = wishService.getWishListsByMemberId(memberId);
        model.addAttribute("wishlistsById", wishLists);
        return "personal-wishcycles";
    }

    @GetMapping("/wishlists/new")
    public String addNewWishlist(Model model) {
        WishList wishList = new WishList();
        model.addAttribute("wishlist", wishList);
        return "add-new-wishlist";
    }

    @PostMapping("/wishlists/create")
    public String createNewWishList(@ModelAttribute WishList wishList, @ModelAttribute Member member) {
        wishService.createWishList(wishList, member);
        return "redirect:/personal-wishcycles " + member.getMemberId();
    }




//    BRUGES NÅR VI VIL TJEKKE OM DE ER LOGGET IND, REDIRECTER TIL LOGIN SIDEN HVIS DE IKKE ER
//    HttpSession session = request.getSession(false);
//
//    if (session == null || session.getAttribute("member") == null) {
//        return redirect:/wishcycle/login;
//    }

}


