package com.example.wishcycle.repository.jdbc;

import com.example.wishcycle.model.Member;
import com.example.wishcycle.repository.mapper.MemberMapper;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbc;
    private final MemberMapper memberMapper;
    private static final String FIND_BY_USERNAME_SQL = "SELECT * FROM wish_user WHERE username = ?";
    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM wish_user WHERE user_email = ?";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM wish_user WHERE user_id = ?";
    private static final String DELETE_MEMBER_SQL = "DELETE FROM wish_user WHERE user_id = ?";
    private static final String CREATE_MEMBER_SQL = "INSERT INTO wish_user (username, user_email, user_password) VALUES (?, ?, ?)";
    private static final String UPDATE_MEMBER_SQL = "UPDATE wish_user SET username = ?, user_email = ?, user_password = ? WHERE user_id = ?";

    public MemberRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.memberMapper = new MemberMapper();
    }

    public Member getMemberById(Long id) {
        return jdbc.queryForObject(FIND_BY_ID_SQL, memberMapper, id);
    }

    public Member getMemberByEmail(String email) {
        List<Member> results = jdbc.query(FIND_BY_EMAIL_SQL, memberMapper, email);
        return results.isEmpty() ? null : results.get(0);
    }

    public Member getMemberByUsername(String username) {
        List<Member> results = jdbc.query(FIND_BY_USERNAME_SQL, memberMapper, username);
        return results.isEmpty() ? null : results.get(0);
    }

    public void deleteMember(Member member) {
        jdbc.update(DELETE_MEMBER_SQL, member.getMemberId());
    }

    public void createMember(Member member) {
        jdbc.update(CREATE_MEMBER_SQL, member.getName(), member.getEmail(), member.getPassword());
    }

    public void updateMember(Member member) {
        jdbc.update(UPDATE_MEMBER_SQL, member.getName(), member.getEmail(), member.getPassword(), member.getMemberId());
    }

}
