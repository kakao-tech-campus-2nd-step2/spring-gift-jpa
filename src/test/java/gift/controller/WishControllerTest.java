package gift.controller;

import gift.domain.Member;
import gift.domain.Wish;
import gift.dto.WishDTO;
import gift.service.WishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishControllerTest {

    @Mock
    private WishService wishService;

    @InjectMocks
    private WishController wishController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddWish() {
        Wish wish = new Wish();
        Member member = new Member();
        member.setId(1L);

        NativeWebRequest request = mock(NativeWebRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer valid.token.here");

        wishController.addWish(wish, member);

        verify(wishService, times(1)).addWish(member.getId(), wish.getProductName());
    }

    @Test
    void testGetWishes() {
        when(wishService.getWishes(1L)).thenReturn(Collections.emptyList());

        List<WishDTO> wishes = wishController.getWishes(1L);

        assertEquals(0, wishes.size());
    }

    @Test
    void testRemoveWish() {
        Wish wish = new Wish();
        Member member = new Member();
        member.setId(1L);

        NativeWebRequest request = mock(NativeWebRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer valid.token.here");

        wishController.removeWish(wish, member);

        verify(wishService, times(1)).removeWish(member.getId(), wish.getProductName());
    }
}
