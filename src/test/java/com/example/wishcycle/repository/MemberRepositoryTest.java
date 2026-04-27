package com.example.wishcycle.repository;
import com.example.wishcycle.model.Member;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        Member member = memberRepo.getMemberById(1L);
        assertNotNull(member);
        assertEquals("simonBeCh", member.getName());
        assertEquals(1L, member.getMemberId());
        assertEquals("sich0008@stud.ek.dk", member.getEmail());
        assertEquals("112pizza", member.getPassword());
    }

    @Test
    void updateMemberCheck() {
        Member updatedMember = new Member();
        updatedMember.setMemberId(2L);
        updatedMember.setName("unhackable");
        updatedMember.setEmail("testemail@gmail.com");
        updatedMember.setPassword("donthackmeplease");

        memberRepo.updateMember(updatedMember);

        assertNotNull(memberRepo.getMemberById(2L));

        Member retrievedMember = memberRepo.getMemberById(2L);

        assertEquals("unhackable", retrievedMember.getName());
        assertEquals("donthackmeplease", retrievedMember.getPassword());
        assertEquals(2L, retrievedMember.getMemberId());
        assertEquals("testemail@gmail.com", retrievedMember.getEmail());
    }

    @Test
    void createMemberTest() {
        Member createdMember = new Member();
        createdMember.setName("newMembahBabeh");
        createdMember.setEmail("newuser@gmail.com");
        createdMember.setPassword("ineededanewaccount");
        memberRepo.createMember(createdMember);

        Member retrievedMember = memberRepo.getMemberByEmail("newuser@gmail.com");
        assertEquals("newMembahBabeh", retrievedMember.getName());
        assertEquals("ineededanewaccount", retrievedMember.getPassword());
        assertNotNull(retrievedMember.getMemberId());
        assertEquals("newuser@gmail.com", retrievedMember.getEmail());
    }

    @Test
    void deleteMember() {
        Member memberToDelete = new Member(4L,"deletemeplease", "iwannabedeleted", "deletemenow@gmail.com");
        memberRepo.createMember(memberToDelete);
        memberRepo.deleteMember(memberToDelete);
        Integer memberCount = jdbc.queryForObject("SELECT COUNT(*) FROM wish_user", Integer.class);
        System.out.println("Number of members in DataBase: " + memberCount);
        assertEquals(3, memberCount);
    }


}
