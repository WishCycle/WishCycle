package com.example.wishcycle.controller;
import com.example.wishcycle.model.Item;
import com.example.wishcycle.model.Member;
import com.example.wishcycle.model.WishList;
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

    public WishController(WishService wishService, MemberService memberService) {
        this.wishService = wishService;
        this.memberService = memberService;
    }

    @GetMapping("/social-wishlists/{memberId}")
    public String getWishLists(Model model, @PathVariable Long memberId) {
        List<WishList> wishLists = wishService.getOtherWishLists(memberId);
        Member member = memberService.getMemberById(memberId);
        model.addAttribute("member",member);
        model.addAttribute("wishlists", wishLists);
        return "social-wishcycles";
    }

    @GetMapping("/wishlists/{memberId}")
    public String getWishListsByMemberId(Model model, @PathVariable Long memberId) {
        List<WishList> wishLists = wishService.getWishListsByMemberId(memberId);
        Member member = memberService.getMemberById(memberId);
        model.addAttribute("member",member);
        model.addAttribute("wishlists", wishLists);
        return "personal-wishcycles";
    }

    @GetMapping("/wishlists/new")
    public String addNewWishlist(Model model) {
        WishList wishList = new WishList();
        model.addAttribute("wishlist", wishList);
        return "add-new-wishlist";
    }

    @GetMapping("/wishlist/add/item")
    public String addItem(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        return "add-new-item";
    }

    @PostMapping("/wishlists/create")
    public String createNewWishList(@ModelAttribute WishList wishList, @ModelAttribute Member member) {
        wishService.createWishList(wishList, member);
        return "add-new-wishlist";
    }

    @PostMapping("/wishlists/update")
    public String updateWishlist(@ModelAttribute WishList wishList, @ModelAttribute Member member) {
        wishService.updateWishList(wishList, member);
        return "redirect:/wishcycle/wishlists/" + member.getMemberId();
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
        wishService.createItem(item);
        wishService.addItemToWishList(wishList, item);
        return "redirect:/wishcycle/wishlists/" + member.getMemberId();
    }

    @PostMapping("/wishlist/delete/item")
    public String deleteItem(@ModelAttribute WishList wishList, @ModelAttribute Item item, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        wishService.setDeleteItemFromWishList(wishList, item);
        return "redirect:/wishcycle/wishlists/" + member.getMemberId();
    }

    @PostMapping("/item/update/item")
    public String updateItem(@ModelAttribute WishList wishList, @ModelAttribute Item item, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        wishService.updateItem(item);
        wishService.setUpdateItemFromWishList(wishList, item);
        return "redirect:/wishcycle/wishlists/" + member.getMemberId();
    }
}


