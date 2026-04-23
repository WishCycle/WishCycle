package com.example.wishcycle.repository;
import com.example.wishcycle.model.Item;
import com.example.wishcycle.model.Member;
import com.example.wishcycle.model.WishList;
import com.example.wishcycle.repository.jdbc.WishListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.net.URL;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // Rolls back after every test so they each live on their own
@ActiveProfiles("test")
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void contextLoad() {} // Checks Application context can start - Looks for correct bean configuration.

    @Test
    void checkH2schemaFile() {
        URL url = getClass().getClassLoader().getResource("h2init.sql");
        System.out.println("URL= " + url);
    }

    @Test
    void wishListsInDatabaseCount() {
        Integer wishListCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM wish_list", Integer.class);
        System.out.println("Number of wishlists: " + wishListCount);
//        assertEquals(3, wishListCount, "Count should match seeded database data");
    }

    @Test
    void checkWishListDataExists() {
        List<WishList> wishLists = repository.findByUserId(1L); // Simons wishlist
        assertNotNull(wishLists);
        assertThat(wishLists.size()).isEqualTo(1);
        assertThat(wishLists.getFirst().getWishListName()).isEqualTo("Simons ønskeliste");
        assertThat(wishLists.getFirst().getDescription()).isEqualTo("Simons ønskeliste for hans fødselsdag");
        assertThat(wishLists.getFirst().getWishListId()).isEqualTo(1L); // AUTO incremented
    }

    // CRUD TEST for wishlist manipulation
    @Test
    void updateWishListCheck() {
        // OLD DATA = wish_list ('Jokkes mokke','Jokkes liste til alt han mangler', 2),
        WishList updatedWishList = new WishList();
        updatedWishList.setWishListId(2L);
        updatedWishList.setWishListName("NewName");
        updatedWishList.setDescription("NewDESC");

        Member member = new Member();
        member.setMemberId(2L);

        List<WishList> updatedList = repository.updateWishList(updatedWishList, 2L);

        assertNotNull(updatedList);
        boolean nameUpdated = false;

        for (WishList wl : updatedList) {
            if (wl.getWishListName().equals("NewName") && wl.getDescription().equals("NewDESC")) {
                nameUpdated = true;
                break;
            }
        }
        assertTrue(nameUpdated, "The wishlist name should have been updated in the database");
    }

    @Test
    void createNewWishList() {
        // Current h2 file has 3 users
        jdbcTemplate.update("INSERT INTO wish_user (username, user_password, user_email) VALUES (?, ?, ?)", "Jack", "testCode", "test.email@gmail.com");
        Long userId = jdbcTemplate.queryForObject("SELECT user_id FROM wish_user WHERE username = 'Jack'", Long.class);

        Member member = new Member();
        member.setMemberId(userId);

        WishList newTestWishList = new WishList(4L, "Create wishlist name", "This wishlist is for test only", member);
        repository.createWishList(newTestWishList, member.getMemberId());

        List<WishList> seededWishlists = repository.findAll();

        wishListsInDatabaseCount(); // Calling wishListsInDatabase COUNT method (Should NOW return 4)
        assertEquals("This wishlist is for test only", newTestWishList.getDescription());

        // Deeper assertion than just the count. We now check for the exact wishlist names that exists in the database
        List<String> namesFromWishLists = seededWishlists.stream().map(WishList::getWishListName).toList();
        List<String> expectedNamesFromSeededWishlists = List.of("Simons ønskeliste", "Jokkes mokke", "Emils traktor liste", "Create wishlist name");
        assertIterableEquals(expectedNamesFromSeededWishlists, namesFromWishLists);
    }

    @Test
    void deleteWishList() {
        repository.findByUserId(1L); // Checks if wishlist_id actually exists
        repository.deleteWishList(1L);

        List<WishList> seededDatabaseWishLists = repository.findAll();

        List<Long> count = seededDatabaseWishLists.stream().map(WishList::getWishListId).toList();
        List<Long> expectedCount = List.of(2L, 3L);

        assertThat(count).isEqualTo(expectedCount); // Checking that database only contains to wishlists after deleting the first one
    }

    // CRUD TEST for item manipulation
    @Test
    void createItem() {
        Item newItem = new Item(7L, "Pants", "Something you wear", "Random//URL.com", 300L);
        repository.createItem(newItem);

        List<Item> seededItems = repository.getAllItems();

        List<String> itemNames = seededItems.stream().map(Item::getItemName).toList();
        List<String> expectedItemNames = List.of("Calvin Klein boxer shorts",
                                                "Algebra (Graduate Texts in Mathematics)",
                                                "Titleist Pro V1",
                                                "Vibox Gaming PC - RTX 5090",
                                                "Rolly Toys Trettraktor",
                                                "DIRE STRAITS - LOVE OVER GOLD (Vinyl)",
                                                "Pants");

        assertIterableEquals(expectedItemNames, itemNames);
    }

    @Test
    void deleteItem() {
        Item itemToDelete = new Item(7L, "ItemIWant", "This item will be deleted", "www.itemIWant.com", 200L);
        repository.createItem(itemToDelete);
        repository.deleteItem(itemToDelete);

        Integer itemsInDatabaseCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM item", Integer.class);
        System.out.println("After deletion there should be 6 items: " + itemsInDatabaseCount);

        int expectedCount = 6;

        assertThat((itemsInDatabaseCount)).isEqualTo(expectedCount);
    }

    // CRUD TEST for wishlist and item manipulation
    @Test
    void addItemToWishList() {
        jdbcTemplate.update("INSERT INTO wish_user (username, user_password, user_email) VALUES (?, ?, ?)", "Jack", "testCode", "test.email@gmail.com");
        Long userId = jdbcTemplate.queryForObject("SELECT user_id FROM wish_user WHERE username = 'Jack'", Long.class);

        Long wishListId = 100L;
        jdbcTemplate.update("INSERT INTO wish_list (wishList_id, wishlist_name, wishlist_desc, user_id) VALUES (?, ?, ?, ?)", wishListId, "JackWishes", "This is the description", userId);

        Item item = new Item();
        item.setItemName("Shoes");
        item.setItemDescription("Something you wear");
        item.setUrl("Random//URL.com");
        item.setPrice(300L);

        repository.createItem(item);

        Member member = new Member();
        member.setMemberId(userId);

        WishList wishList = new WishList();
        wishList.setWishListId(wishListId);

        repository.addItemToWishList(wishList, item);

        Integer wishListLineInDatabaseAdded = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM wish_list_item WHERE wishlist_id = ? AND item_id = ?", Integer.class, wishListId, item.getItemId());
        assertEquals(1, wishListLineInDatabaseAdded, "Should be exactly one new line");

        String checkForDescription = jdbcTemplate.queryForObject("SELECT wish_description FROM wish_list_item WHERE wishlist_id = ? AND item_id = ?", String.class, wishListId, item.getItemId());
        assertEquals("Something you wear", checkForDescription);
    }

    @Test
    void deleteItemFromWishlist() {
        jdbcTemplate.update("INSERT INTO wish_user (username, user_password, user_email) VALUES (?, ?, ?)", "Jack", "testCode", "test.email@gmail.com");
        Long userId = jdbcTemplate.queryForObject("SELECT user_id FROM wish_user WHERE username = 'Jack'", Long.class);

        Long wishListId = 100L;
        jdbcTemplate.update("INSERT INTO wish_list (wishList_id, wishlist_name, wishlist_desc, user_id) VALUES (?, ?, ?, ?)", wishListId, "JackWishes", "This is the description", userId);

        Item item = new Item();
        item.setItemName("Shoes");
        item.setItemDescription("Something you wear");
        item.setUrl("Random//URL.com");
        item.setPrice(300L);

        repository.createItem(item);

        Member member = new Member();
        member.setMemberId(userId);

        WishList wishList = new WishList();
        wishList.setWishListId(wishListId);

        repository.addItemToWishList(wishList, item);
        repository.setDeleteItemFromWishlist(wishList, item);

        List<Item> seededItems = repository.itemsInWishList(100L);

        assertThat(seededItems.size()).isEqualTo(0);
    }

    @Test
    void updateItemOnWishList() {
        WishList wishList = new WishList();
        Long wishlistId = 2L;
        wishList.setWishListId(wishlistId);
        wishList.setWishListName("Jokkes mokke");
        wishList.setDescription("Jokkes liste til alt han mangler");

        String newDesc = "Golfbolde fra Titleist description";
        wishList.setDescription(newDesc);

        Item itemToUpdate = new Item();
        Long itemId = 3L;
        itemToUpdate.setItemId(itemId);
        itemToUpdate.setItemName("Pro v1");
        itemToUpdate.setUrl("https://www.amazon.de/dp/B0DPN7QZ9R");
        Long expectedPrice = 999L;
        itemToUpdate.setPrice(expectedPrice);

        repository.setUpdateItemOnWishlist(wishList, itemToUpdate);

        Item resultItem = jdbcTemplate.queryForObject("SELECT * FROM item WHERE item_id = ?", new BeanPropertyRowMapper<>(Item.class), itemId);

        String wishDescription = jdbcTemplate.queryForObject("SELECT wish_description FROM wish_list_item WHERE wishlist_id = ? AND item_id = ?", String.class, wishlistId, itemId);

        assertThat(resultItem).isNotNull();
        assertThat(resultItem.getItemName()).isEqualTo("Pro v1");
        assertEquals(expectedPrice, itemToUpdate.getPrice());
        assertThat(wishDescription).isEqualTo("Golfbolde fra Titleist description");
    }
}
