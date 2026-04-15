package com.example.wishcycle.model;

public class Item {

    private Long itemId;
    private String itemName;
    private String itemDescription;
    private String url;
    private Long price;

    public Item(Long wishId, String wishName, String wishDescription, String url, Long price) {

        this.itemId = wishId;
        this.itemName = wishName;
        this.itemDescription = wishDescription;
        this.url = url;
        this.price = price;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public String getItemDescription() {
        return itemDescription;
    }
}
