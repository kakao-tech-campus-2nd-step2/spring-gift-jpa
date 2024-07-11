package gift.wishlist.presentation;

import gift.auth.TokenService;
import gift.wishlist.application.WishlistResponse;
import gift.wishlist.application.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        WishlistResponse response = new WishlistResponse(1L, 1L, 1L);
        when(wishlistService.findByMemberId(anyLong())).thenReturn(List.of(response));

        // When & Then
        mockMvc.perform(get("/api/wishlist")
                        .header("Authorization", "Bearer " + token)
                        .requestAttr("memberId", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].memberId").value(1L))
                .andExpect(jsonPath("$[0].productId").value(1L));

        verify(wishlistService, times(1)).findByMemberId(1L);
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

        verify(wishlistService, times(0)).findByMemberId(anyLong());
    }

    @Test
    void 위시리스트_삭제_인가받지않은_사용자_테스트() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/wishlist/1"))
                .andExpect(status().isUnauthorized());

        verify(wishlistService, times(0)).delete(anyLong());
    }
}
