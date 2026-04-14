package com.example.wishcycle.repository.mapper;

import com.example.wishcycle.model.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class WishListMapper {

    private final JdbcTemplate jdbc;

    public WishListMapper(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<WishList> GetWishListByUser() {
        List<WishList> wishLists = jdbc.query("SELECT * FROM wish_list WHERE wish_list.user_id", rowMapper);
        return wishLists;
    }

    private final RowMapper<WishList> rowMapper = ((rs, rowNum) -> {
        WishList wishList = new WishList();
        wishList.setWishListId(rs.getLong("id"));
        wishList.setWishListName(rs.getString("name"));
        return wishList;
    });
}
