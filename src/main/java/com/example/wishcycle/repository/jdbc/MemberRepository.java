package com.example.wishcycle.repository.jdbc;

import com.example.wishcycle.model.Member;
import com.example.wishcycle.repository.mapper.MemberMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbc;
    private final MemberMapper memberMapper;

    private static final String FIND_BY_USERNAME_SQL = "SELECT * FROM wish_user WHERE username = ?";
    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM wish_user WHERE user_email = ?";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM wish_user WHERE id = ?";
    private static final String DELETE_MEMBER_SQL = "DELETE FROM wish_user WHERE user_id = ?";
    private static final String CREATE_MEMBER_SQL = "INSERT INTO wish_user (username, user_email, user_password) VALUES (?, ?, ?)";


    public MemberRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.memberMapper = new MemberMapper();
    }

    public Member getMemberById(int id) {
        return jdbc.queryForObject(FIND_BY_ID_SQL, memberMapper, id);
    }

    public Member getMemberByEmail(String email) {
        return jdbc.queryForObject(FIND_BY_EMAIL_SQL, memberMapper, email);

    }

    public Member getMemberByUsername(String username) {
        return jdbc.queryForObject(FIND_BY_USERNAME_SQL, memberMapper, username);
    }

    public void deleteMember(Member member) {
        jdbc.update(DELETE_MEMBER_SQL, memberMapper, member.getMemberId());
    }

    public void createMember(Member member) {
        jdbc.update(CREATE_MEMBER_SQL, memberMapper, member.toString(), member.getEmail(), member.getPassword());
    }
}
