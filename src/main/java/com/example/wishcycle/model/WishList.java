package com.example.wishcycle.model;
import java.util.HashMap;

public class WishList {

    private int wishListId;
    private String wishListName;
    private String description;
    private Member member;

    public WishList(int wishListId, String wishListName, String description, Member member) {
        this.wishListId = wishListId;
        this.wishListName = wishListName;
        this.description = description;
        this.member = member;
    }

    public WishList() {}

    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

    public int getWishListId() {
        return wishListId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWishListName() {
        return wishListName;
    }

    public void setWishListName(String wishListName) {
        this.wishListName = wishListName;
    }

}
