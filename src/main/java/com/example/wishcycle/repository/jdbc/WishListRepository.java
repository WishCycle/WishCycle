package com.example.wishcycle.repository.jdbc;

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

    public WishListRepository(JdbcTemplate jdbc, WishListMapper wishListMapper) {
        this.jdbc = jdbc;
        this.wishListMapper = wishListMapper;
    }

    public List<WishList> findByUserId(Long userId) {
        return jdbc.query(FIND_BY_USER_SQL, wishListMapper, userId);
    }
}