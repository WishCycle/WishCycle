package com.example.wishcycle.controller;

import com.example.wishcycle.model.Item;
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

    @PostMapping("/wishlists/update")
    public String updateWishlist(@ModelAttribute WishList wishList, @ModelAttribute Member member) {
        wishService.updateWishList(wishList, member);
        return "redirect:/wishlist";
    }

    @PostMapping("/wishlists/delete")
    public String deleteWishlist(@ModelAttribute WishList wishlist, @ModelAttribute Member member) {
        wishService.deleteWishList(wishlist, member);
        return "redirect:/wishlist";
    }

    @GetMapping("/wishlist/add")
    public String addItem(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        return "add-new-item";
    }

    @PostMapping("/wishlist/item")
    public String createItem(@ModelAttribute Item item) {
        wishService.createItem(item);
        return "redirect:/wishlist";
    }





//    BRUGES NÅR VI VIL TJEKKE OM DE ER LOGGET IND, REDIRECTER TIL LOGIN SIDEN HVIS DE IKKE ER
//    HttpSession session = request.getSession(false);
//
//    if (session == null || session.getAttribute("member") == null) {
//        return redirect:/wishcycle/login;
//    }

}


