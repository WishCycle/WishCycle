package com.example.wishcycle.repository.mapper;
import com.example.wishcycle.model.WishList;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WishListMapper implements RowMapper<WishList> {

    @Override
    public WishList mapRow(ResultSet rs, int rowNum) throws SQLException {
        WishList wishList = new WishList();
        wishList.setWishListId(rs.getLong("wishlist_id"));
        wishList.setWishListName(rs.getString("wishlist_name"));
        wishList.setDescription(rs.getString("wishlist_desc"));
        return wishList;
    }


}
