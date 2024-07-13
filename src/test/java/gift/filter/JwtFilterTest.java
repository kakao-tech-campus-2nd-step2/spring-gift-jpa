package gift.filter;

import gift.util.UserUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.mockito.Mockito.*;

@SpringBootTest
public class JwtFilterTest {
    private String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3QxQG5hdmVyLmNvbSJ9.-9C8Mmec3xwhwzcFer-S3MDbGXJcI0P2YQZFdHIdF_U";

    private JwtFilter jwtFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @Autowired
    private UserUtility userUtility;
    @Value("${spring.var.token-prefix}")
    private String tokenPrefix;

    @BeforeEach
    void setup() {
        jwtFilter = new JwtFilter(tokenPrefix, userUtility);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }

    @Test
    void doFilterWithoutTokenTest() throws ServletException, IOException {
        // given
        when(request.getHeader("Authorization")).thenReturn(null);

        // when
        jwtFilter.doFilter(request, response, filterChain);

        // then
        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Test
    void doFilterWithValidTokenTest() throws ServletException, IOException {
        // given
        when(request.getHeader("Authorization")).thenReturn(tokenPrefix + validToken);

        // when
        jwtFilter.doFilter(request, response, filterChain);

        // then
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterWithInvalidTokenTest() throws ServletException, IOException {
        // given
        when(request.getHeader("Authorization")).thenReturn(tokenPrefix + validToken + "invalid");

        // when
        jwtFilter.doFilter(request, response, filterChain);

        // then
        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
