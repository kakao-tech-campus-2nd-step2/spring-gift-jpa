package gift.service;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.WishlistItem;
import gift.dto.request.WishlistNameRequest;
import gift.exception.MemberNotFoundException;
import gift.repository.member.MemberSpringDataJpaRepository;
import gift.repository.product.ProductSpringDataJpaRepository;
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

    @Mock
    private MemberSpringDataJpaRepository memberRepository;

    @Mock
    private ProductSpringDataJpaRepository productRepository;

    @InjectMocks
    private WishlistService wishlistService;

    private final static Long MEMBER_ID = 1L;
    private final static Long PRODUCT_ID = 100L;

    private Member member;
    private Product product;
    private String validToken = "valid_token";

    @BeforeEach
    public void setup() {
        member = new Member("test@example.com", "password");
        member.setId(MEMBER_ID);

        product = new Product("Product 1", 100, "test-url");
        product.setId(PRODUCT_ID);

    }

    @Test
    public void testAddItemToWishlist() {
        when(tokenService.getMemberIdFromToken(validToken)).thenReturn(MEMBER_ID.toString());
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));

        WishlistNameRequest request = new WishlistNameRequest(MEMBER_ID, PRODUCT_ID);
        WishlistItem wishlistItem = new WishlistItem(member, product);

        when(wishlistRepository.save(any(WishlistItem.class))).thenReturn(wishlistItem);

        wishlistService.addItemToWishlist(request, validToken);

        verify(wishlistRepository, times(1)).save(any(WishlistItem.class));
    }


    @Test
    public void testDeleteItemFromWishlist() {
        when(tokenService.getMemberIdFromToken(validToken)).thenReturn(MEMBER_ID.toString());
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));

        WishlistItem wishlistItem = new WishlistItem(member, product);
        when(wishlistRepository.findByMemberId(MEMBER_ID)).thenReturn(Arrays.asList(wishlistItem));

        wishlistService.deleteItemFromWishlist(PRODUCT_ID, validToken);

        verify(wishlistRepository, times(1)).deleteByMemberIdAndProductId(MEMBER_ID, PRODUCT_ID);
    }


    @Test
    public void testDeleteItemFromWishlistMemberNotFound() {
        when(tokenService.getMemberIdFromToken(validToken)).thenReturn(MEMBER_ID.toString());
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));

        when(wishlistRepository.findByMemberId(MEMBER_ID)).thenReturn(Arrays.asList());

        assertThrows(MemberNotFoundException.class, () -> {
            wishlistService.deleteItemFromWishlist(PRODUCT_ID, validToken);
        });

        verify(wishlistRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testGetWishlistByMemberId() {
        Product product2 = new Product("Product 2", 200, "test-url-2");
        product2.setId(2L);
        List<WishlistItem> expectedItems = Arrays.asList(
                new WishlistItem(member, product),
                new WishlistItem(member, product2)
        );

        when(wishlistRepository.findByMemberId(MEMBER_ID)).thenReturn(expectedItems);

        List<WishlistItem> actualItems = wishlistService.getWishlistByMemberId(MEMBER_ID);

        assertEquals(expectedItems.size(), actualItems.size());
        assertEquals(expectedItems.get(0).getProduct().getId(), actualItems.get(0).getProduct().getId());
        assertEquals(expectedItems.get(1).getProduct().getId(), actualItems.get(1).getProduct().getId());
    }
}
