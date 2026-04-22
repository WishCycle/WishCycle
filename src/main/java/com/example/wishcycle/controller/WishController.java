package com.example.wishcycle.controller;
import com.example.wishcycle.model.Item;
import com.example.wishcycle.model.Member;
import com.example.wishcycle.model.WishList;
import com.example.wishcycle.repository.mapper.WishListMapper;
import com.example.wishcycle.service.MemberService;
import com.example.wishcycle.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wishcycle")
public class WishController {

    private final WishService wishService;
    private final MemberService memberService;
    private final WishListMapper wishListMapper;

    public WishController(WishService wishService, MemberService memberService, WishListMapper wishListMapper) {
        this.wishService = wishService;
        this.memberService = memberService;
        this.wishListMapper = wishListMapper;
    }

    @GetMapping("/social-wishlists/{memberId}")
    public String getWishLists(Model model, @PathVariable Long memberId) {
        List<WishList> wishLists = wishService.getOtherWishLists(memberId);
        for(WishList w : wishLists){
            wishService.getItemsByWishlistId(w, w.getWishListId());
        }
        Member member = memberService.getMemberById(memberId);
        model.addAttribute("member",member);
        model.addAttribute("wishlists", wishLists);
        return "social-wishcycles";
    }

    @GetMapping("/wishlists/{memberId}")
    public String getWishListsByMemberId(Model model, @PathVariable Long memberId) {
        List<WishList> wishLists = wishService.getWishListsByMemberId(memberId);
        for(WishList w : wishLists){
            wishService.getItemsByWishlistId(w, w.getWishListId());
        }
        Member member = memberService.getMemberById(memberId);
        model.addAttribute("member",member);
        model.addAttribute("wishlists", wishLists);
        return "personal-wishcycles";
    }

    @GetMapping("/wishlists/new")
    public String addNewWishlist(Model model, @ModelAttribute Member member) {
        WishList wishList = new WishList();
        model.addAttribute("wishlist", wishList);
        return "add-new-wishlist";
    }

    @GetMapping("/wishlist/add/item")
    public String addItem(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        List<WishList> wishLists = wishService.getWishListsByMemberId(member.getMemberId());
        model.addAttribute("wishlists", wishLists);
        model.addAttribute("item", new Item());
        model.addAttribute("newWishlist", new WishList());
        return "add-new-item";
    }

    @GetMapping("/wishlist/view/{wId}")
    public String viewSingleWishlist(@PathVariable Long wId, Model model) {
        WishList wishList = wishService.getWishListById(wId);
        List<Item> items = wishService.getItemsByWishlistId(wishList, wId);
        model.addAttribute("wishlist", wishList);
        model.addAttribute("items", items);
        return "wishlist";
    }

    @PostMapping("/wishlist/create")
    public String createNewWishList(@ModelAttribute WishList wishList, HttpSession session) {
        Member sessionMember = (Member) session.getAttribute("member");
        wishService.createWishList(wishList, sessionMember.getMemberId());
        return "redirect:/wishcycle/wishlists/" + sessionMember.getMemberId();
    }


    @PostMapping("/wishlists/delete")
    public String deleteWishlist(@ModelAttribute WishList wishlist, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        wishService.deleteWishList(wishlist, member);
        return "redirect:/wishcycle/wishlists/" + member.getMemberId();
    }

    @PostMapping("/wishlist/create/item")
    public String createItem(@ModelAttribute WishList wishList, @ModelAttribute Item item, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        wishService.addItemToWishList(wishList, item);
        wishService.getWishListsByMemberId(member.getMemberId());
        return "redirect:/wishcycle/wishlist/view/" + member.getMemberId();
    }

    @PostMapping("/wishlist/delete/item")
    public String deleteItem(@ModelAttribute WishList wishList, @ModelAttribute Item item, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        wishService.setDeleteItemFromWishList(wishList, item);
        return "redirect:/wishcycle/wishlists/" + member.getMemberId();
    }


    @PostMapping("/personal-wishcycles/wishlist/view/{id}")
    public String updateItemOnWishlist(@ModelAttribute WishList wishlist, @ModelAttribute Item item, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        wishService.setUpdateItemFromWishList(wishlist, item);
        return "redirect:/wishcycle/wishlist/view/" + member.getMemberId();
    }
}


