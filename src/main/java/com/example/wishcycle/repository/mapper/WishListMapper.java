package com.example.wishcycle.repository.mapper;
import com.example.wishcycle.model.WishList;
import org.springframework.jdbc.core.RowMapper;

public class WishListMapper {

    private final RowMapper<WishList> rowMapper = ((rs, rowNum) -> {
        WishList wishList = new WishList();
        wishList.setWishListId(rs.getLong("id"));
        wishList.setWishListName(rs.getString("name"));
        return wishList;
    });
}
