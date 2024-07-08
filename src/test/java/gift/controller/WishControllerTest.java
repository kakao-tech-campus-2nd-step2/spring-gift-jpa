package gift.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import gift.dto.wish.WishCreateRequest;
import gift.dto.wish.WishRequest;
import gift.dto.wish.WishResponse;
import gift.service.MemberService;
import gift.service.WishService;
import jakarta.servlet.http.HttpServletRequest;
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
    private HttpServletRequest request;

    @InjectMocks
    private WishController wishController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getAttribute("memberId")).thenReturn(1L);
        when(wishService.getMemberIdFromRequest(request)).thenReturn(1L);
    }

    @Test
    @DisplayName("위시리스트 조회")
    public void testGetWishlist() {
        WishResponse wishResponse = new WishResponse(1L, 1L, 1L);
        when(wishService.getWishlistByMemberId(1L)).thenReturn(List.of(wishResponse));

        ResponseEntity<List<WishResponse>> response = wishController.getWishlist(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("위시리스트 항목 추가")
    public void testAddWish() {
        WishCreateRequest wishCreateRequest = new WishCreateRequest(1L);
        WishResponse wishResponse = new WishResponse(1L, 1L, 1L);

        when(wishService.addWish(any(WishRequest.class))).thenReturn(wishResponse);

        ResponseEntity<WishResponse> response = wishController.addWish(wishCreateRequest, request);
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
