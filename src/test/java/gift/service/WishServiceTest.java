package gift.service;

import static gift.util.Constants.PRODUCT_NOT_FOUND;
import static gift.util.Constants.WISH_ALREADY_EXISTS;
import static gift.util.Constants.WISH_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import gift.dto.product.ProductResponse;
import gift.dto.wish.WishRequest;
import gift.dto.wish.WishResponse;
import gift.exception.product.ProductNotFoundException;
import gift.exception.wish.DuplicateWishException;
import gift.exception.wish.WishNotFoundException;
import gift.model.Wish;
import gift.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class WishServiceTest {

    @Mock
    private WishRepository wishRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private WishService wishService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("위시리스트에 상품 추가 성공 테스트")
    public void testAddWishSuccess() {
        WishRequest wishRequest = new WishRequest(1L, 1L);
        Wish wish = new Wish(1L, 1L, 1L);
        ProductResponse productResponse = new ProductResponse(1L, "Product", 100, "imageUrl");

        when(productService.getProductById(1L)).thenReturn(productResponse);
        when(wishRepository.existsByMemberIdAndProductId(1L, 1L)).thenReturn(false);
        when(wishRepository.create(any(Wish.class))).thenReturn(wish);

        WishResponse response = wishService.addWish(wishRequest);
        assertEquals(1L, response.memberId());
        assertEquals(1L, response.productId());
        assertNotNull(response.id());
    }

    @Test
    @DisplayName("위시리스트에 없는 상품 ID 추가 시도")
    public void testAddWishProductNotFound() {
        WishRequest wishRequest = new WishRequest(1L, 999L);

        when(productService.getProductById(999L)).thenThrow(new ProductNotFoundException(PRODUCT_NOT_FOUND + 999));

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            wishService.addWish(wishRequest);
        });

        assertEquals(PRODUCT_NOT_FOUND + "999", exception.getMessage());
    }

    @Test
    @DisplayName("위시리스트에 이미 존재하는 상품 추가 시도")
    public void testAddWishDuplicate() {
        WishRequest wishRequest = new WishRequest(1L, 1L);
        ProductResponse productResponse = new ProductResponse(1L, "Product", 100, "imageUrl");

        when(productService.getProductById(1L)).thenReturn(productResponse);
        when(wishRepository.existsByMemberIdAndProductId(1L, 1L)).thenReturn(true);

        DuplicateWishException exception = assertThrows(DuplicateWishException.class, () -> {
            wishService.addWish(wishRequest);
        });

        assertEquals(WISH_ALREADY_EXISTS, exception.getMessage());
    }

    @Test
    @DisplayName("위시리스트에서 상품 삭제 성공 테스트")
    public void testDeleteWishSuccess() {
        Wish wish = new Wish(1L, 1L, 1L);

        when(wishRepository.findById(1L)).thenReturn(Optional.of(wish));

        wishService.deleteWish(1L);
    }

    @Test
    @DisplayName("위시리스트에서 없는 상품 ID로 삭제 시도")
    public void testDeleteWishNotFound() {
        when(wishRepository.findById(999L)).thenReturn(Optional.empty());

        WishNotFoundException exception = assertThrows(WishNotFoundException.class, () -> {
            wishService.deleteWish(999L);
        });

        assertEquals(WISH_NOT_FOUND + "999", exception.getMessage());
    }

    @Test
    @DisplayName("회원의 위시리스트 조회 테스트")
    public void testGetWishlistByMemberId() {
        Wish wish = new Wish(1L, 1L, 1L);
        when(wishRepository.findAllByMemberId(1L)).thenReturn(List.of(wish));

        List<WishResponse> wishlist = wishService.getWishlistByMemberId(1L);
        assertEquals(1, wishlist.size());
        assertEquals(1L, wishlist.getFirst().memberId());
        assertEquals(1L, wishlist.getFirst().productId());
    }
}
