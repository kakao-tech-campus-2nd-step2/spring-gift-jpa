package gift.interceptor;

import gift.RepositoryMockConfiguration;
import gift.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@SpringJUnitConfig(RepositoryMockConfiguration.class)
class AuthInterceptorTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TokenService tokenService;

    @Test
    @DisplayName("유효한 토큰 전달 테스트")
    void testValidToken() throws Exception {
        String token = "valid_token";
        when(tokenService.isValidateToken(token)).thenReturn(true);
        when(tokenService.getMemberIdByToken(token)).thenReturn(1L);

        mockMvc.perform(get("/api/wishlist")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tokenService, times(1)).isValidateToken(token);
        verify(tokenService, times(1)).getMemberIdByToken(token);
    }

    @Test
    @DisplayName("유효하지 않은 토큰 전달 테스트")
    void testInvalidToken() throws Exception {
        String token = "invalid_token";
        when(tokenService.isValidateToken(token)).thenReturn(false);

        mockMvc.perform(get("/api/wishlist/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(tokenService, times(1)).isValidateToken(token);
        verify(tokenService, never()).getMemberIdByToken(anyString());
    }

}
