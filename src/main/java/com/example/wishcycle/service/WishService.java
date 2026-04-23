package com.example.wishcycle.service;
import com.example.wishcycle.model.Item;
import com.example.wishcycle.model.Member;
import com.example.wishcycle.model.WishList;
import com.example.wishcycle.repository.jdbc.MemberRepository;
import com.example.wishcycle.repository.jdbc.WishListRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class WishService {

    @Autowired
    private final WishListRepository wishListRepository;
    private final MemberRepository memberRepository;

    public WishService(WishListRepository wishListRepository, MemberRepository memberRepository) {
        this.wishListRepository = wishListRepository;
        this.memberRepository = memberRepository;
    }

    public List<WishList> getWishListsByMemberId(Long memberId) {
        if (wishListRepository.findByUserId(memberId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No WishList found for member: " + memberRepository.getMemberById(memberId));
        }
        return wishListRepository.findByUserId(memberId);
    }

    public WishList getWishListById(Long id) {
        return wishListRepository.findByWishlistId(id);
    }

    public List<WishList> getOtherWishLists(Long memberId) {
        return wishListRepository.findAllOthers(memberId);
    }

    public void createWishList(WishList wishList, Long memberId) {
        if (wishList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Try Again! Create a valid WishList!");
        }
        if (wishListRepository.findByUserId(memberId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account you are trying to create the WishList with is not valid. Try logging in again!");
        }
        wishListRepository.createWishList(wishList, memberId);
    }

    public void updateWishList(WishList wishList, Member member) {
        if (wishList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You must create a WishList first!");
        }
        if (wishListRepository.findByUserId(member.getMemberId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account you are trying to update a WishList with is not valid. Try logging in again!");
        }
        wishListRepository.updateWishList(wishList, member.getMemberId());
    }

    public void deleteWishList(WishList wishList, Member member) {
        if (wishList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The WishList you are trying to delete does not exist.");
        }

        if (!wishList.getMember().getMemberId().equals(member.getMemberId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to dele others wishlists");
        }
        wishListRepository.deleteWishList(wishList.getWishListId());
    }

    public void addItemToWishList(WishList wishList, Item item) {
        if (wishList == null || item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        wishListRepository.addItemToWishList(wishList, item);
    }

    public void setDeleteItemFromWishList(WishList wishList, Item item) {
        if (wishList == null || item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        wishListRepository.setDeleteItemFromWishlist(wishList, item);
    }

    public void setUpdateItemFromWishList(WishList wishlist, Item item) {
        if (wishlist == null || item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        wishListRepository.setUpdateItemOnWishlist(wishlist, item);
    }

    public List<Item> getItemsByWishlistId(WishList wishList, Long id) {
        List<Item> items = wishListRepository.itemsInWishList(id);
        wishList.setItems(items);
        return items;
    }
}
