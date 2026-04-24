package com.example.wishcycle.service;
import com.example.wishcycle.model.Member;
import com.example.wishcycle.model.WishList;
import com.example.wishcycle.repository.jdbc.WishListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WishServiceTest {

    @Mock
    private WishListRepository wishListRepository;

    @InjectMocks
    private WishService wishService;
    private WishList wishlist;
    private Member member;

    @BeforeEach
    public void setUp() {
        member = new Member(1L, "Joakim", "PASSWORD", "Joakim@gmail.com");
        wishlist = new WishList(1L, "Joakims Wishlist", "Test description", new Member());
        wishService.createWishList(wishlist, member.getMemberId());
    }

    @Test
    public void testGetWishlistById() {
        when(wishListRepository.findByUserId(1L)).thenReturn(List.of(wishlist));

        List<WishList> result = wishService.getWishListsByMemberId(1L);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        verify(wishListRepository, times(3)).findByUserId(1L);
    }

    @Test
    public void testCreateWishlist() {
        when(wishListRepository.findByUserId(1L)).thenReturn(List.of(wishlist));

        List<WishList> result = wishService.getWishListsByMemberId(1L);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getWishListName()).isEqualTo("Joakims Wishlist");
        assertThat(result.getFirst().getDescription()).isEqualTo("Test description");
    }

    @Test
    public void testDeleteWishlist() {
        doNothing().when(wishListRepository).deleteWishList(1L);

        wishService.deleteWishList(wishlist, member);

        verify(wishListRepository, times(1)).deleteWishList(1L);
    }

    @Test
    public void testUpdateWishList() {
        when(wishListRepository.findByUserId(1L)).thenReturn(List.of(wishlist));

        WishList updatedList = new WishList(1L, "Joakims Birthday Wishlist", "Test Description for this updated version", member);

        wishService.updateWishList(updatedList, member);

        assertThat(updatedList.getWishListId().equals(1L));
        assertThat(updatedList.getWishListName().equals("Joakim Birthday Wishlist"));
        assertThat(updatedList.getDescription().equals("Test Description for this updated version"));
    }
}
