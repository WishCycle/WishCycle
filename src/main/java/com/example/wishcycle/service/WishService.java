package com.example.wishcycle.service;

import com.example.wishcycle.model.Item;
import com.example.wishcycle.model.Member;
import com.example.wishcycle.model.WishList;
import com.example.wishcycle.repository.jdbc.MemberRepository;
import com.example.wishcycle.repository.jdbc.WishListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        return  wishListRepository.findAll();
    }

    public List<WishList> getWishListsByMemberId(int memberId) {
        if (wishListRepository.findByUserId(memberId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No WishList found for member: " + memberRepository.getMemberById(memberId));
        }
        return wishListRepository.findByUserId(memberId);
    }

    public void createWishList(WishList wishList, Member member) {
        if (wishList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Try Again! Create a valid WishList!");
        }
        if (wishListRepository.findByUserId(member.getMemberId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account you are trying to create the WishList with is not valid. Try logging in again!");
        }
        wishListRepository.createWishList(wishList, member);
    }

    public void updateWishList(WishList wishList, Member member) {
        if (wishList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You must create a WishList first!");
        }
        if (wishListRepository.findByUserId(member.getMemberId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account you are trying to update a WishList with is not valid. Try logging in again!");
        }

        wishListRepository.updateWishList(wishList,member.getMemberId());
    }


    public void deleteWishList(WishList wishList, Member member) {
        if (wishList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The WishList you are trying to delete does not exist.");
        }
        if (wishListRepository.findByUserId(member.getMemberId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account you are trying to delete a WishList with is not valid. Try logging in again!");
        }
        wishListRepository.deleteWishList(wishList.getWishListId());

    }

    public void createItem(Item item) {
        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The item you are trying to create is invalid. Try again.");
        }
        wishListRepository.createItem(item);
    }

    public void deleteItem(Item item) {
        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The item you are trying to delete does not exist. Try again.");
        }
        wishListRepository.deleteItem(item);
    }

    public void updateItem(Item item) {
        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The item you are trying to update could not be found. Try again.");
        }
        wishListRepository.updateItem(item);
    }




}
