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

    public WishService(MemberRepository memberRepository, WishListRepository wishListRepository) {
        this.memberRepository = memberRepository;
        this.wishListRepository = wishListRepository;
    }

    public List<WishList> getWishLists() {

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
