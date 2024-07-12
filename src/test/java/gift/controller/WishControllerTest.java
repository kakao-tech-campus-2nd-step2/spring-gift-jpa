package gift.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import gift.dto.member.MemberResponse;
import gift.dto.product.ProductResponse;
import gift.dto.wish.WishCreateRequest;
import gift.dto.wish.WishRequest;
import gift.dto.wish.WishResponse;
import gift.model.Member;
import gift.model.Product;
import gift.service.MemberService;
import gift.service.ProductService;
import gift.service.WishService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class WishControllerTest {

    @Mock
    private WishService wishService;

    @Mock
    private MemberService memberService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private WishController wishController;

    private Member member;
    private Product product;
    private MemberResponse memberResponse;
    private ProductResponse productResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        member = new Member(1L, "test@example.com", "password");
        product = new Product(1L, "Product1", 100, "imageUrl1");

        memberResponse = new MemberResponse(1L, "test@example.com", null);
        productResponse = new ProductResponse(1L, "Product1", 100, "imageUrl1");
    }

    @Test
    @DisplayName("위시리스트 조회")
    public void testGetWishlist() {
        WishResponse wishResponse = new WishResponse(1L, member, product);
        when(wishService.getWishlistByMemberId(1L)).thenReturn(List.of(wishResponse));

        ResponseEntity<List<WishResponse>> response = wishController.getWishlist(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("위시리스트 항목 추가")
    public void testAddWish() {
        WishCreateRequest wishCreateRequest = new WishCreateRequest(1L);
        WishResponse wishResponse = new WishResponse(1L, member, product);

        when(memberService.getMemberById(1L)).thenReturn(memberResponse);
        when(productService.getProductById(1L)).thenReturn(productResponse);
        when(memberService.convertToEntity(memberResponse)).thenReturn(member);
        when(productService.convertToEntity(productResponse)).thenReturn(product);
        when(wishService.addWish(any(WishRequest.class))).thenReturn(wishResponse);

        ResponseEntity<WishResponse> response = wishController.addWish(wishCreateRequest, 1L);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());
    }

    @Test
    @DisplayName("위시리스트 항목 삭제")
    public void testDeleteWish() {
        ResponseEntity<Void> response = wishController.deleteWish(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
