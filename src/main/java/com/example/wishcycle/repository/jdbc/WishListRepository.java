package com.example.wishcycle.repository.jdbc;

import com.example.wishcycle.repository.mapper.WishListMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishListRepository {

    private final JdbcTemplate jdbc;
    private final WishListMapper wishListMapper = new WishListMapper();

    public WishListRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
}