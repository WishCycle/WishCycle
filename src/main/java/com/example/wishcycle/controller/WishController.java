package com.example.wishcycle.controller;
import com.example.wishcycle.model.Item;
import com.example.wishcycle.model.Member;
import com.example.wishcycle.model.WishList;
import com.example.wishcycle.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/wishcycle")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping("/social-wishlists/{memberId}")
    public String getWishLists(Model model, @PathVariable Long memberId) {
        List<WishList> wishLists = wishService.getOtherWishLists(memberId);
        model.addAttribute("wishlists", wishLists);
        return "social-wishcycles";
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
        return "redirect:/wishlist/wishlists/{memberId}";
    }

    @PostMapping("/wishlists/delete")
    public String deleteWishlist(@ModelAttribute WishList wishlist, @ModelAttribute Member member) {
        wishService.deleteWishList(wishlist, member);
        return "redirect:/wishcycle/wishlists/{memberId}";
    }

    @PostMapping("/wishlist/create/item")
    public String createItem(@ModelAttribute WishList wishList, @ModelAttribute Item item) {
        wishService.createItem(item);
        wishService.addItemToWishList(wishList, item);
        return "redirect:/wishcycle/wishlists/{memberId}";
    }

    @PostMapping("/wishlist/delete/item")
    public String deleteItem(@ModelAttribute WishList wishList, @ModelAttribute Item item) {
        wishService.setDeleteItemFromWishList(wishList, item);
        return "redirect:/wishcycle/wishlists/{memberId}";
    }

    @PostMapping("/item/update/item")
    public String updateItem(@ModelAttribute WishList wishList, @ModelAttribute Item item) {
        wishService.updateItem(item);
        wishService.setUpdateItemFromWishList(wishList, item);
        return "redirect:/wishcycle/wishlists/{memberId}";
    }
}


