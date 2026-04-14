package com.example.wishcycle.model;

public class Wish {

    private Long wishId;
    private String wishName;
    private String wishDescription;
    private String url;
    private Long price;

    public Wish(Long wishId, String wishName, String wishDescription, String url, Long price) {

        this.wishId = wishId;
        this.wishName = wishName;
        this.wishDescription = wishDescription;
        this.url = url;
        this.price = price;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public String getWishDescription() {
        return wishDescription;
    }
}
