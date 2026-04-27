package com.example.wishcycle.controller;
import com.example.wishcycle.model.Member;
import com.example.wishcycle.model.WishList;
import com.example.wishcycle.service.MemberService;
import com.example.wishcycle.service.WishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class WishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WishService wishService;

    @MockitoBean
    private MemberService memberService;

    private WishList testWishlist;
    private Member testMember;

    @BeforeEach
    void setUp() {
        testMember = new Member();
        testMember.setMemberId(1L);
        testMember.setName("Joakim");

        testWishlist = new WishList();
        testWishlist.setWishListId(100L);
        testWishlist.setWishListName("My Birthday 2026");
    }

//    @Test
//    void shouldGetWishListsByMemberIdTest() throws Exception {
//
//        when(memberService.getMemberById(1L)).thenReturn(testMember);
//        when(wishService.getWishListsByMemberId(1L)).thenReturn(List.of(testWishlist));
//
//        mockMvc.perform(get("/wishcycle/wishlists/1"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("personal-wishcycles"))
//                .andExpect(model().attribute("member", testMember))
//                .andExpect(model().attribute("wishlists", Collections.singletonList(testWishlist)));
//    }

}
