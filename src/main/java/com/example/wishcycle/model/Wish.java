package com.example.wishcycle.model;

public class Wish {

    private Long wishId;
    private String wishName;
    private String wishDescription;
    private String wishLink;

    public Wish(Long wishId, String wishName, String wishDescription, String wishLink) {

        this.wishId = wishId;
        this.wishName = wishName;
        this.wishDescription = wishDescription;
        this.wishLink = wishLink;
    }

    public Long getWishId() {
        return wishId;
    }

    public String getWishName() {
        return wishName;
    }

    public void setWishName(String wishName) {
        this.wishName = wishName;
    }
}
