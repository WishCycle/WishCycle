package com.example.wishcycle.model;

public class Wish {

    private Long wishId;
    private String wishName;

    public Wish(Long wishId, String wishName) {

        this.wishId = wishId;
        this.wishName = wishName;
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
