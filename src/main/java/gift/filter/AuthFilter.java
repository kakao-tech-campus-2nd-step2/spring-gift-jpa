package gift.filter;

import gift.domain.TokenAuth;
import gift.repository.TokenRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    private final TokenRepository tokenRepository;

    @Autowired
    public AuthFilter(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        if (isUnauthenticatedPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            httpResponse.sendRedirect("/home");
            return;
        }

        String token = authHeader.substring(7);

        if (!isTokenValid(token)) {
            httpResponse.sendRedirect("/home");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private boolean isUnauthenticatedPath(String path) {
        return path.equals("/home") || path.startsWith("/members") || path.startsWith("/h2-console");
    }

    private boolean isTokenValid(String token) {
        TokenAuth tokenAuth = tokenRepository.findTokenByToken(token).orElse(null);
        return tokenAuth != null;
    }
}
