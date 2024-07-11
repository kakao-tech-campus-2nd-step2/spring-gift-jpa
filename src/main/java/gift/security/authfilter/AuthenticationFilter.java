package gift.security.authfilter;

import gift.security.jwt.TokenExtractor;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class AuthenticationFilter implements Filter {
    private final TokenExtractor tokenExtractor;

    public AuthenticationFilter(TokenExtractor tokenExtractor) {
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = ((HttpServletRequest) servletRequest).getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token does not exist or format is invalid.");
            return;
        }

        String jwtToken = token.substring(7);
        if (!tokenExtractor.validateToken(jwtToken)) {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
