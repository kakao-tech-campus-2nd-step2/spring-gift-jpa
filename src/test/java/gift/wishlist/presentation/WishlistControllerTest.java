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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WishlistController.class)
@Import({TokenService.class}) // TokenService를 빈으로 등록
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
        token = "valid_token"; // 테스트용 JWT 토큰 설정
        when(tokenService.extractEmail(anyString())).thenReturn("test@example.com");
    }

    @Test
    public void 위시리스트_추가_테스트() throws Exception {
        // Given
        doNothing().when(wishlistService).add(anyString(), anyLong());

        // When & Then
        mockMvc.perform(post("/api/wishlist")
                        .header("Authorization", "Bearer " + token)
                        .param("productId", "1")
                        .requestAttr("email", "test@example.com"))
                .andExpect(status().isOk());

        verify(wishlistService, times(1)).add("test@example.com", 1L);
    }

    @Test
    public void 모든_위시리스트_조회_테스트() throws Exception {
        // Given
        WishlistResponse response = new WishlistResponse(1L, "test@example.com", 1L);
        when(wishlistService.findAllByMember(anyString())).thenReturn(List.of(response));

        // When & Then
        mockMvc.perform(get("/api/wishlist")
                        .header("Authorization", "Bearer " + token)
                        .requestAttr("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].memberEmail").value("test@example.com"));

        verify(wishlistService, times(1)).findAllByMember("test@example.com");
    }

    @Test
    public void 위시리스트_삭제_테스트() throws Exception {
        // Given
        doNothing().when(wishlistService).delete(anyLong());

        // When & Then
        mockMvc.perform(delete("/api/wishlist/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        verify(wishlistService, times(1)).delete(1L);
    }

    @Test
    public void 위시리스트_추가_인가받지않은_사용자_테스트() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/wishlist")
                        .param("productId", "1"))
                .andExpect(status().isUnauthorized());

        verify(wishlistService, times(0)).add(anyString(), anyLong());
    }

    @Test
    public void 모든_위시리스트_조회_인가받지않은_사용자_테스트() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/wishlist"))
                .andExpect(status().isUnauthorized());

        verify(wishlistService, times(0)).findAllByMember(anyString());
    }

    @Test
    public void 위시리스트_삭제_인가받지않은_사용자_테스트() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/wishlist/1"))
                .andExpect(status().isUnauthorized());

        verify(wishlistService, times(0)).delete(anyLong());
    }
}
