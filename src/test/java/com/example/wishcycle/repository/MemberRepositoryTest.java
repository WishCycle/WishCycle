package com.example.wishcycle.repository;

import com.example.wishcycle.repository.jdbc.MemberRepository;
import com.example.wishcycle.repository.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")

public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    private MemberMapper mapper;

    @Test
    void contextLoad() {}

    @Test
    void checkH2schemaFile() {
        URL url = getClass().getClassLoader().getResource("h2init.sql");
        System.out.println("URL= " + url);
    }

    @Test
    void membersInDataBaseCount(){
        Integer memberCount = jdbc.queryForObject("SELECT COUNT(*) FROM wish_user", Integer.class);
        System.out.println("Number of members in DataBase: " + memberCount);
        assertEquals(3, memberCount);
    }


    @Test
    void checkCorrectMemberFound(){

    }


}
