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

    private static final String GET_ALL_WISH_LISTS = "SELECT * FROM wish_list";
    private static final String FIND_BY_USER_SQL = "SELECT * FROM wish_list WHERE user_id = ?";
    private static final String DELETE_WISHLIST = "DELETE FROM wish_list WHERE wishlist_id = ?";
    private static final String CREATE_NEW_WISHLIST = "INSERT INTO wish_list (wishList_id, wishlist_name, wishlist_desc, user_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_WISHLIST = "UPDATE wish_list SET wishlist_name = ?, wishlist_desc = ? WHERE wishlist_id = ?";

    private static final String CREATE_ITEM = "INSERT INTO item (item_id, item_name, item_url, item_price) VALUES (?, ?, ?, ?)";
    private static final String DELETE_ITEM = "DELETE FROM item WHERE item_id = ?";
    private static final String UPDATE_ITEM = "UPDATE item SET item_name = ?, item_url = ?, item_price = ? WHERE item_id";

    private static final String ADD_ITEM_TO_WISHLIST = "INSERT INTO wish_list_item (wishlist_id, item_id, wish_description) VALUES (?, ?, ?)";
    private static final String DELETE_ITEM_FROM_WISHLIST = "DELETE FROM wish_list_item WHERE item(item_id) = ?";
    private static final String UPDATE_ITEM_ON_WISHLIST =
            "UPDATE item JOIN wish_list_item ON item.item_id = wish_list_item.item_id " +
            "SET item.item_name = ?, item.item_url = ?, item.item_price = ? " +
            "WHERE wish_list_item.wishlist_id = ?";


    public WishListRepository(JdbcTemplate jdbc, WishListMapper wishListMapper) {
        this.jdbc = jdbc;
        this.wishListMapper = wishListMapper;
    }

    public List<WishList> findAll() {
        return jdbc.query(GET_ALL_WISH_LISTS, wishListMapper);
    }

    public List<WishList> findByUserId(Long userId) {
        return jdbc.query(FIND_BY_USER_SQL, wishListMapper, userId);
    }

    public void deleteWishList(Long wishListId) {
        jdbc.update(DELETE_WISHLIST, wishListId);
    }

    public void createWishList(WishList wishList, Member member) {
        jdbc.update(CREATE_NEW_WISHLIST, wishList.getWishListId(), wishList.getWishListName(), wishList.getDescription(), member.getMemberId());
    }

    public List<WishList> updateWishList(WishList wishList, Long userId) {
        jdbc.update(UPDATE_WISHLIST, wishList.getWishListName(), wishList.getDescription(), wishList.getWishListId());
        return findByUserId(userId);
    }

    public void createItem(Item item) {
        jdbc.update(CREATE_ITEM, item.getItemId(), item.getItemName(), item.getUrl(), item.getPrice());
    }

    public void deleteItem(Item item) {
        jdbc.update(DELETE_ITEM, item.getItemId());
    }

    public Item updateItem(Item item) {
        jdbc.update(UPDATE_ITEM, item.getItemName(), item.getUrl(), item.getPrice());
        return item;
    }

}