package gift.service;

import gift.domain.WishlistItem;
import gift.dto.request.WishlistNameRequest;
import gift.exception.MemberNotFoundException;
import gift.repository.wishlist.WishlistSpringDataJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WishlistServiceTest {

    @Mock
    private WishlistSpringDataJpaRepository wishlistRepository;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private WishlistService wishlistService;

    private final static Long MEMBER_ID = 1L;
    private final static Long PRODUCT_ID = 100L;

    @BeforeEach
    public void setup() {
        when(tokenService.getMemberIdFromToken(anyString())).thenReturn(MEMBER_ID.toString());
    }

    @Test
    public void testAddItemToWishlist() {
        WishlistNameRequest request = new WishlistNameRequest(MEMBER_ID, PRODUCT_ID);
        wishlistService.addItemToWishlist(request, "dummy_token");

        verify(wishlistRepository, times(1)).save(any(WishlistItem.class));
    }


    @Test
    public void testDeleteItemFromWishlist() {
        WishlistItem wishlistItem = new WishlistItem(MEMBER_ID, PRODUCT_ID);
        when(wishlistRepository.findByMemberId(MEMBER_ID))
                .thenReturn(Arrays.asList(wishlistItem));

        wishlistService.deleteItemFromWishlist(PRODUCT_ID, "dummy_token");

        verify(wishlistRepository, times(1)).delete(wishlistItem);
    }


    @Test
    public void testDeleteItemFromWishlistMemberNotFound() {
        when(wishlistRepository.findByMemberId(MEMBER_ID))
                .thenReturn(Arrays.asList());

        assertThrows(MemberNotFoundException.class,
                () -> wishlistService.deleteItemFromWishlist(PRODUCT_ID, "dummy_token"));

        verify(wishlistRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testGetWishlistByMemberId() {
        List<WishlistItem> expectedItems = Arrays.asList(
                new WishlistItem(MEMBER_ID, PRODUCT_ID),
                new WishlistItem(MEMBER_ID, PRODUCT_ID + 1)
        );

        when(wishlistRepository.findByMemberId(MEMBER_ID)).thenReturn(expectedItems);

        List<WishlistItem> actualItems = wishlistService.getWishlistByMemberId(MEMBER_ID);

        assertEquals(expectedItems.size(), actualItems.size());
        assertEquals(expectedItems.get(0).getProductId(), actualItems.get(0).getProductId());
        assertEquals(expectedItems.get(1).getProductId(), actualItems.get(1).getProductId());
    }
}
