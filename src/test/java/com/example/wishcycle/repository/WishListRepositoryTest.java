package com.example.wishcycle.repository;
import com.example.wishcycle.model.Member;
import com.example.wishcycle.model.WishList;
import com.example.wishcycle.repository.jdbc.WishListRepository;
import com.example.wishcycle.repository.mapper.WishListMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private WishListMapper wishListMapper;

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
        System.out.println("Number of wishlist: " + wishListCount);
    }

    @Test
    void checkWishListDataExists() {
        List<WishList> wishLists = repository.findByUserId(1); // Simons wishlist
        assertNotNull(wishLists);
        assertThat(wishLists.size()).isEqualTo(1);
        assertThat(wishLists.getFirst().getWishListName()).isEqualTo("Simons ønskeliste");
        assertThat(wishLists.getFirst().getDescription()).isEqualTo("Simons ønskeliste for hans fødselsdag");
        assertThat(wishLists.getFirst().getWishListId()).isEqualTo(1L); // AUTO incremented
    }

    @Test
    void updateWishListCheck() {
        // OLD DATA = wish_list ('Jokkes mokke','Jokkes liste til alt han mangler', 2),
        WishList updatedWishList = new WishList();
        updatedWishList.setWishListId(2L);
        updatedWishList.setWishListName("NewName");
        updatedWishList.setDescription("NewDESC");

        Member member = new Member();
        member.setMemberId(2);

        List<WishList> updatedList = repository.updateWishList(updatedWishList, 2);

        assertNotNull(updatedList);
        boolean nameUpdated = false;

        for (WishList wl : updatedList) {
            if (wl.getWishListName().equals("NewName")) {
                nameUpdated = true;
                break;
            }
        }
        assertTrue(nameUpdated, "The wishlist name should have been updated in the database");
    }
}
