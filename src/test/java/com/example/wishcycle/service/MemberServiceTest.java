package com.example.wishcycle.service;
import com.example.wishcycle.model.Member;
import com.example.wishcycle.repository.jdbc.MemberRepository;
import org.h2.value.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.openmbean.OpenType;
import javax.xml.transform.Result;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;
    private Member savedMember;

    @BeforeEach
    public void setUp() {
        savedMember = new Member(1L, "Jack", "PASSWORD", "Jack@gmail.com");
    }

   @Test
    public void testGetMemberById() {
       when(memberRepository.getMemberById(1L)).thenReturn(savedMember);

       Member result = memberService.getMemberById(1L);

       assertEquals(1L, (long) result.getMemberId());
       assertEquals("Jack", result.getName());
       verify(memberRepository, times(1)).getMemberById(1L);
   }

   @Test
    public void testCreateMember() {
       memberService.createMember(savedMember);

       assertEquals("Jack", savedMember.getName());
       assertEquals("PASSWORD", savedMember.getPassword());
       assertEquals("Jack@gmail.com", savedMember.getEmail());
       assertEquals(Long.valueOf(1L), savedMember.getMemberId());
   }

   @Test
    public void testDeleteMember() {
        when(memberRepository.getMemberById(1L)).thenReturn(savedMember);

        memberService.deleteMember(savedMember);

        verify(memberRepository, times(1)).deleteMember(savedMember);
   }
}
