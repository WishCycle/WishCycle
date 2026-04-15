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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private WishListMapper wishListMapper;

    @BeforeEach
    void setUp() {
        Member member = new Member(1, "Jack", "password123", "Jack@gmail.com");
        WishList wishList = new WishList(1L, "Fødselsdag", "Min fødselsdag i året 2026", member);
        repository.createWishList(wishList, member);
    }

    @Test
    void contextLoad() {} // Tjekker Application context kan starte. Er beans konfigureret korrekt.

    @Test
    void checkH2schemaFile() {
        URL url = getClass().getClassLoader().getResource("h2init.sql");
        System.out.println("URL= " + url);
    }

    @Test
    void displayAllWishLists() {

        List<WishList> wishListList = repository.findByUserId(1);

        Integer wishListCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM wish_list", Integer.class);
        System.out.println("Number of wishlist: " + wishListCount);

        assertNotNull(wishListList);


    }










}
