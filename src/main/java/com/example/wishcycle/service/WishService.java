package com.example.wishcycle.service;

import com.example.wishcycle.model.Item;
import com.example.wishcycle.model.WishList;
import com.example.wishcycle.repository.jdbc.MemberRepository;
import com.example.wishcycle.repository.jdbc.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final MemberRepository memberRepository;
    private final WishListRepository wishListRepository;
    private final ItemRepository wishRepository;


    public WishService(MemberRepository memberRepository, WishListRepository wishListRepository, ItemRepository wishRepository) {
        this.memberRepository = memberRepository;
        this.wishListRepository = wishListRepository;
        this.wishRepository = wishRepository;
    }

    public List<WishList> getPublicWishLists() {

    }

    public List<WishList> getPrivateWishLists() {

    }

    public void createWishList(WishList wishList) {

    }

    public void updateWishList(WishList wishList, List<Item> list) {

    }

    public void deleteWishList(WishList wishList) {

    }

    public void createWish(Item wish) {

    }

    public void deleteWish(Item wish) {

    }




}
