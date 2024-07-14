package gift.wishlist.presentation;

import gift.auth.TokenService;
import gift.wishlist.application.WishlistResponse;
import gift.wishlist.application.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WishlistController.class)
public class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistService wishlistService;

    @MockBean
    private TokenService tokenService;

    private String token;

    @BeforeEach
    public void setUp() {
        token = "valid_token";
        when(tokenService.extractMemberId(anyString())).thenReturn(1L);
    }

    @Test
    void 위시리스트_추가_테스트() throws Exception {
        // Given
        doNothing().when(wishlistService).save(anyLong(), anyLong());

        // When & Then
        mockMvc.perform(post("/api/wishlist")
                        .header("Authorization", "Bearer " + token)
                        .param("productId", "1")
                        .requestAttr("memberId", 1L))
                .andExpect(status().isOk());

        verify(wishlistService, times(1)).save(1L, 1L);
    }

    @Test
    void 모든_위시리스트_조회_테스트() throws Exception {
        // Given
        WishlistResponse response1 = new WishlistResponse(1L, 1L, 1L);
        WishlistResponse response2 = new WishlistResponse(2L, 1L, 2L);
        WishlistResponse response3 = new WishlistResponse(3L, 1L, 3L);
        WishlistResponse response4 = new WishlistResponse(4L, 1L, 4L);
        WishlistResponse response5 = new WishlistResponse(5L, 1L, 5L);
        Page<WishlistResponse> firstPage = new PageImpl<>(List.of(response1, response2, response3), PageRequest.of(0, 3), 5);
        Page<WishlistResponse> secondPage = new PageImpl<>(List.of(response4, response5), PageRequest.of(1, 3), 5);

        when(wishlistService.findAllByMemberId(eq(1L), any(Pageable.class)))
                .thenReturn(firstPage)
                .thenReturn(secondPage);

        // When & Then
        mockMvc.perform(get("/api/wishlist")
                        .header("Authorization", "Bearer " + token)
                        .requestAttr("memberId", 1L)
                        .param("page", "0")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3))
                .andExpect(jsonPath("$.content[0].memberId").value(1L))
                .andExpect(jsonPath("$.content[0].productId").value(1L))
                .andExpect(jsonPath("$.content[1].memberId").value(1L))
                .andExpect(jsonPath("$.content[1].productId").value(2L))
                .andExpect(jsonPath("$.content[2].memberId").value(1L))
                .andExpect(jsonPath("$.content[2].productId").value(3L));

        mockMvc.perform(get("/api/wishlist")
                        .header("Authorization", "Bearer " + token)
                        .requestAttr("memberId", 1L)
                        .param("page", "1")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].memberId").value(1L))
                .andExpect(jsonPath("$.content[0].productId").value(4L))
                .andExpect(jsonPath("$.content[1].memberId").value(1L))
                .andExpect(jsonPath("$.content[1].productId").value(5L));

        verify(wishlistService, times(2)).findAllByMemberId(eq(1L), any(Pageable.class));
    }

    @Test
    void 특정_상품의_위시리스트_조회_테스트() throws Exception {
        // Given
        WishlistResponse response1 = new WishlistResponse(1L, 1L, 1L);
        WishlistResponse response2 = new WishlistResponse(2L, 1L, 1L);
        WishlistResponse response3 = new WishlistResponse(3L, 1L, 1L);
        WishlistResponse response4 = new WishlistResponse(4L, 1L, 1L);
        WishlistResponse response5 = new WishlistResponse(5L, 1L, 1L);
        Page<WishlistResponse> firstPage = new PageImpl<>(List.of(response1, response2, response3), PageRequest.of(0, 3), 5);
        Page<WishlistResponse> secondPage = new PageImpl<>(List.of(response4, response5), PageRequest.of(1, 3), 5);

        when(wishlistService.findAllByProductId(eq(1L), any(Pageable.class)))
                .thenReturn(firstPage)
                .thenReturn(secondPage);

        // When & Then
        mockMvc.perform(get("/api/wishlist/{productId}", 1L)
                        .header("Authorization", "Bearer " + token)
                        .param("page", "0")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3))
                .andExpect(jsonPath("$.content[0].memberId").value(1L))
                .andExpect(jsonPath("$.content[0].productId").value(1L))
                .andExpect(jsonPath("$.content[1].memberId").value(1L))
                .andExpect(jsonPath("$.content[1].productId").value(1L))
                .andExpect(jsonPath("$.content[2].memberId").value(1L))
                .andExpect(jsonPath("$.content[2].productId").value(1L));

        mockMvc.perform(get("/api/wishlist/{productId}", 1L)
                        .header("Authorization", "Bearer " + token)
                        .param("page", "1")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].memberId").value(1L))
                .andExpect(jsonPath("$.content[0].productId").value(1L))
                .andExpect(jsonPath("$.content[1].memberId").value(1L))
                .andExpect(jsonPath("$.content[1].productId").value(1L));

        verify(wishlistService, times(2)).findAllByProductId(eq(1L), any(Pageable.class));
    }

    @Test
    void 위시리스트_삭제_테스트() throws Exception {
        // Given
        doNothing().when(wishlistService).delete(anyLong());

        // When & Then
        mockMvc.perform(delete("/api/wishlist/{id}", 1L)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        verify(wishlistService, times(1)).delete(1L);
    }

    @Test
    void 위시리스트_추가_인가받지않은_사용자_테스트() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/wishlist")
                        .param("productId", "1"))
                .andExpect(status().isUnauthorized());

        verify(wishlistService, times(0)).save(anyLong(), anyLong());
    }

    @Test
    void 모든_위시리스트_조회_인가받지않은_사용자_테스트() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/wishlist"))
                .andExpect(status().isUnauthorized());

        verify(wishlistService, times(0)).findAllByMemberId(anyLong(), any(Pageable.class));
    }

    @Test
    void 위시리스트_삭제_인가받지않은_사용자_테스트() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/wishlist/1"))
                .andExpect(status().isUnauthorized());

        verify(wishlistService, times(0)).delete(anyLong());
    }
}
