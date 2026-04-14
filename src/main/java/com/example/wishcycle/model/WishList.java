package com.example.wishcycle.model;
import java.util.HashMap;

public class WishList {

    private Long wishListId;
    private String wishListName;
    private String description;

    public WishList(Long wishListId, String wishListName, String description) {
        this.wishListId = wishListId;
        this.wishListName = wishListName;
        this.description = description;
    }

    public WishList() {}

    public void setWishListId(Long wishListId) {
        this.wishListId = wishListId;
    }

    public Long getWishListId() {
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
