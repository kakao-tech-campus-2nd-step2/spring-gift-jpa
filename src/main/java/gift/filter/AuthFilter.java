package gift.filter;

import gift.domain.AuthToken;
import gift.repository.token.TokenRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public class AuthFilter implements Filter {

    private final TokenRepository tokenRepository;

    public AuthFilter(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        // Filter 를 통과하지 않아도 되는 url
        if (path.equals("/home") || path.startsWith("/members") || path.startsWith("/h2-console")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Authorization header 존재하는지 확인
        // 없으면 누구나 접근할 수 있는 페이지로 리다이렉트
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader == null || authHeader.isEmpty()){
            httpResponse.sendRedirect("/home");
            return;
        }

        // bearer 인증방식을 이용하기 때문에 header 7번째 문자부터 유효한 key 값
        // key 값이 DB에 저장되어 있지 않다면 발급받아야 함
        // 없으면 누구나 접근할 수 있는 페이지로 리다이렉트
        Optional<AuthToken> token = tokenRepository.findAuthTokenByToken(authHeader.substring(7));

        if (token.isEmpty()){
            httpResponse.sendRedirect("/home");
            return;
        }

        filterChain.doFilter(request, response);
    }

}
