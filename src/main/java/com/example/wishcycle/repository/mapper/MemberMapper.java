package com.example.wishcycle.repository.mapper;

import com.example.wishcycle.model.Member;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Long.parseLong;

@Component
public class MemberMapper implements RowMapper<Member> {


    @Override
    public Member mapRow(ResultSet rs, int rownum) throws SQLException {
        Member member = new Member();
        member.setName(rs.getString("username"));
        member.setEmail(rs.getString("user_email"));
        member.setPassword(rs.getString("user_password"));
        member.setMemberId(parseLong(rs.getString("user_id")));

        return member;
    }

}
