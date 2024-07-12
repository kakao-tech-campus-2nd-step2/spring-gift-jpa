package gift.filter;

import gift.domain.TokenAuth;
import gift.repository.token.TokenSpringDataJpaRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class LoginFilter implements Filter {

    private final TokenSpringDataJpaRepository tokenRepository;

    @Autowired
    public LoginFilter(TokenSpringDataJpaRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Optional<TokenAuth> tokenAuthOptional = tokenRepository.findByToken(token);

            if (tokenAuthOptional.isPresent()) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 인증되지 않은 경우 혹은 토큰이 유효하지 않은 경우
        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Override
    public void destroy() {
    }
}
