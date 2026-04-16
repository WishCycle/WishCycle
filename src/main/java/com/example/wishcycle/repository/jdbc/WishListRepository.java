package com.example.wishcycle.repository.jdbc;


import com.example.wishcycle.model.Item;
import com.example.wishcycle.model.Member;
import com.example.wishcycle.model.WishList;
import com.example.wishcycle.repository.mapper.WishListMapper;
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

    private static final String CREATE_ITEM = "INSERT INTO item (item_id, item_name, item_url, item_price) VALUES (?, ?, ?, ?)";
    private static final String DELETE_ITEM = "DELETE FROM item WHERE item_id = ?";
    private static final String UPDATE_ITEM = "UPDATE item SET item_name = ?, item_url = ?, item_price = ? WHERE item_id";

    private static final String ADD_ITEM_TO_WISHLIST = "INSERT INTO wish_list_item (wishlist_id, item_id, wish_description) VALUES (?, ?, ?)";
    private static final String DELETE_ITEM_FROM_WISHLIST = "DELETE FROM wish_list_item WHERE item(item_id) = ?";
    private static final String UPDATE_ITEM_ON_WISHLIST =
            "SELECT item(item_id) FROM wish_list_item LEFT JOIN item ON wish_listItem.item(item_id) = item.item_id UPDATE item SET item_name = ?, item_url = ?, item_price = ?";


    public WishListRepository(JdbcTemplate jdbc, WishListMapper wishListMapper) {
        this.jdbc = jdbc;
        this.wishListMapper = wishListMapper;
    }

    // CRUD Operations for wishlists
    public List<WishList> findByUserId(int userId) {
        return jdbc.query(FIND_BY_USER_SQL, wishListMapper, userId);
    }

    public void deleteWishList(int wishListId) {
        jdbc.update(DELETE_WISHLIST, wishListId);
    }

    public List<WishList> createWishList(WishList wishList, Member member) {
        jdbc.update(CREATE_NEW_WISHLIST, wishList.getWishListId(), wishList.getWishListName(), wishList.getDescription());
        return findByUserId(member.getMemberId());
    }

    public List<WishList> updateWishList(WishList wishList, int userId) {
        jdbc.update(UPDATE_WISHLIST, wishList.getWishListName(), wishList.getDescription(), wishList.getWishListId());
        return findByUserId(userId);
    }

    // CRUD Operations for ITEMS on wishlists
    public void createWish(WishList wishList, Item item) {
        jdbc.update(CREATE_ITEM, item.getItemId(), item.getItemName(), item.getUrl(), item.getPrice());
        jdbc.update(ADD_ITEM_TO_WISHLIST, wishListMapper, wishList.getWishListId(), item.getItemId(), wishList.getDescription());
    }

    public void deleteWish(Item item) {
        jdbc.update(DELETE_ITEM, item.getItemId());
        jdbc.update(DELETE_ITEM_FROM_WISHLIST, wishListMapper, item.getItemId());
    }

    public Item updateWish(Item item) {
        jdbc.update(UPDATE_ITEM, item.getItemName(), item.getUrl(), item.getPrice());
        jdbc.update(UPDATE_ITEM_ON_WISHLIST, wishListMapper, item.getItemName(), item.getUrl(), item.getPrice());
        return item;
    }
}