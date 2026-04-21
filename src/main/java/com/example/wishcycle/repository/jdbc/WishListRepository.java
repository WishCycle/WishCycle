package com.example.wishcycle.repository.jdbc;
import com.example.wishcycle.model.Item;
import com.example.wishcycle.model.Member;
import com.example.wishcycle.model.WishList;
import com.example.wishcycle.repository.mapper.ItemMapper;
import com.example.wishcycle.repository.mapper.WishListMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class WishListRepository {

    private final JdbcTemplate jdbc;
    private final WishListMapper wishListMapper;
    private final ItemMapper itemMapper;

    private static final String GET_ALL_WISH_LISTS = "SELECT * FROM wish_list";
    private static final String FIND_BY_USER_SQL = "SELECT * FROM wish_list WHERE user_id = ?";
    private static final String DELETE_WISHLIST = "DELETE FROM wish_list WHERE wishlist_id = ?";
    private static final String CREATE_NEW_WISHLIST = "INSERT INTO wish_list (wishList_id, wishlist_name, wishlist_desc, user_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_WISHLIST = "UPDATE wish_list SET wishlist_name = ?, wishlist_desc = ? WHERE wishlist_id = ?";
    private static final String GET_OTHER_WIST_LISTS = "SELECT * FROM  wish_user WHERE user_id <> ?";

    private static final String GET_ALL_ITEMS = "SELECT * FROM item";
    private static final String CREATE_ITEM = "INSERT INTO item (item_id, item_name, item_url, item_price) VALUES (?, ?, ?, ?)";
    private static final String DELETE_ITEM = "DELETE FROM item WHERE item_id = ?";
    private static final String UPDATE_ITEM = "UPDATE item SET item_name = ?, item_url = ?, item_price = ? WHERE item_id = ?";

    private static final String GET_ITEM_BY_ID = "SELECT * FROM item WHERE item_id = ?";
    private static final String GET_ALL_ITEMS_IN_WISHLIST = "SELECT i.* FROM item i JOIN wish_list_item wli ON i.item_id = wli.item_id WHERE wli.wishlist_id = ?";
    private static final String ADD_ITEM_TO_WISHLIST = "INSERT INTO wish_list_item (wishlist_id, item_id, wish_description) VALUES (?, ?, ?)";
    private static final String DELETE_ITEM_FROM_WISHLIST = "DELETE FROM wish_list_item WHERE wishlist_id = ? AND item_id = ?";
    private static final String UPDATE_ITEM_ON_WISHLIST = "UPDATE item SET item_name = ?, item_url = ?, item_price = ? WHERE item_id = ? AND item_id IN (SELECT item_id FROM wish_list_item WHERE wishlist_id = ?)";
    private static final String UPDATE_WISH_DESCRIPTION= "UPDATE wish_list_item SET wish_description = ? WHERE wishlist_id = ? AND item_id = ?";

    public WishListRepository(JdbcTemplate jdbc, WishListMapper wishListMapper, ItemMapper itemMapper) {
        this.jdbc = jdbc;
        this.wishListMapper = wishListMapper;
        this.itemMapper = itemMapper;
    }

    // CRUD OPERATIONS for wishlist manipulation
    public List<WishList> findAll() {
        return jdbc.query(GET_ALL_WISH_LISTS, wishListMapper);
    }

    public List<WishList> findAllOthers(Long memberId) {
        return jdbc.query(GET_OTHER_WIST_LISTS, wishListMapper, memberId);
    }

    public List<WishList> findByUserId(Long userId) {
        return jdbc.query(FIND_BY_USER_SQL, wishListMapper, userId);
    }

    public void deleteWishList(Long wishListId) {
        jdbc.update(DELETE_WISHLIST, wishListId);
    }

    public void createWishList(WishList wishList, Long memberId) {
        jdbc.update(CREATE_NEW_WISHLIST, wishList.getWishListId(), wishList.getWishListName(), wishList.getDescription(), memberId);
    }

    public List<WishList> updateWishList(WishList wishList, Long userId) {
        jdbc.update(UPDATE_WISHLIST, wishList.getWishListName(), wishList.getDescription(), wishList.getWishListId());
        return findByUserId(userId);
    }

    // CRUD OPERATIONS for item manipulation
    public List<Item> getAllItems() {
        return jdbc.query(GET_ALL_ITEMS, itemMapper);
    }

    public void createItem(Item item) {
        jdbc.update(CREATE_ITEM, item.getItemId(), item.getItemName(), item.getUrl(), item.getPrice());
    }

    public void deleteItem(Item item) {
        jdbc.update(DELETE_ITEM, item.getItemId());
    }

    public Item updateItem(Item item) {
        jdbc.update(UPDATE_ITEM, item.getItemName(), item.getUrl(), item.getPrice(), item.getItemId());
        return item;
    }

    // CRUD OPERATIONS for wishlist and item manipulation
    public Long getItemById(Item item) {
        jdbc.queryForObject(GET_ITEM_BY_ID, itemMapper);
        return item.getItemId();
    }

    public List<Item> itemsInWishList(Long wishListId) {
        return jdbc.query(GET_ALL_ITEMS_IN_WISHLIST, itemMapper, wishListId);
    }

    public void addItemToWishList(WishList wishList, Item item) {
        jdbc.update(ADD_ITEM_TO_WISHLIST, wishList.getWishListId(), item.getItemId(), item.getItemDescription());
    }

    public void setDeleteItemFromWishlist(WishList wishlist, Item item) {
        jdbc.update(DELETE_ITEM_FROM_WISHLIST, wishlist.getWishListId(), item.getItemId());
    }

    public void setUpdateItemOnWishlist(WishList wishlist, Item item) {
        jdbc.update(UPDATE_ITEM_ON_WISHLIST, item.getItemName(), item.getUrl(), item.getPrice(), item.getItemId(), wishlist.getWishListId());
        jdbc.update(UPDATE_WISH_DESCRIPTION, wishlist.getDescription(), wishlist.getWishListId(), item.getItemId());
    }
}