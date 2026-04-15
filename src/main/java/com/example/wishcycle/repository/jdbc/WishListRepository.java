package com.example.wishcycle.repository.jdbc;


import com.example.wishcycle.model.Item;
import com.example.wishcycle.model.Member;
import com.example.wishcycle.model.WishList;
import com.example.wishcycle.member.mapper.WishListMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishListRepository {

    private final JdbcTemplate jdbc;
    private final WishListMapper wishListMapper;

    private static final String FIND_BY_USER_SQL = "SELECT * FROM wish_list WHERE user_id = ?";
    private static final String DELETE_WISHLIST = "DELETE FROM wish_list WHERE wishlist_id = ?";
    private static final String CREATE_NEW_WISHLIST = "INSERT INTO wish_list (wishlist_id, wishlist_name, wishlist_desc) VALUES (?, ?, ?)";
    private static final String UPDATE_WISHLIST = "UPDATE wish_list SET wishlist_name = ?, wishlist_desc = ? WHERE wishlist_id = ?";

    private static final String CREATE_WISH = "INSERT INTO item (item_id, item_name, item_url, item_price) VALUES (?, ?, ?, ?)";

    public WishListRepository(JdbcTemplate jdbc, WishListMapper wishListMapper) {
        this.jdbc = jdbc;
        this.wishListMapper = wishListMapper;
    }

    public List<WishList> findByUserId(Long userId) {
        return jdbc.query(FIND_BY_USER_SQL, wishListMapper, userId);
    }

    public void deleteWishList(Long wishListId) {
        jdbc.update(DELETE_WISHLIST, wishListId);
    }

    public List<WishList> createWishList(WishList wishList, Member member) {
        jdbc.update(CREATE_NEW_WISHLIST, wishList.getWishListId(), wishList.getWishListName(), wishList.getDescription());
        return findByUserId(member.getMemberId());
    }

    public List<WishList> updateWishList(WishList wishList, Long userId) {
        jdbc.update(UPDATE_WISHLIST, wishList.getWishListName(), wishList.getDescription(), wishList.getWishListId());
        return findByUserId(userId);
    }

//    public Item createWish(Long userId, Item item) {
//        jdbc.query(CREATE_WISH, ,)
//    }

    // Create wish / item
    // Delete wish / item
    // Update wish / item


}