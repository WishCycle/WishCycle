package com.example.wishcycle.model;

import java.util.HashMap;

public class WishList {
    HashMap<Long,Wish> wishList;
    public WishList(HashMap<Long,Wish> wishList){
        this.wishList = wishList;
    }

}
