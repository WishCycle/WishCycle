package com.example.wishcycle.repository.jdbc;

import com.example.wishcycle.repository.mapper.WishListMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepository {

    private final JdbcTemplate jdbc;
    private final WishListMapper wishListMapper = new WishListMapper(new JdbcTemplate());

    public WishRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }









}
