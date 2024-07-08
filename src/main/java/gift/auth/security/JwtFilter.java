package gift.auth.security;

import gift.error.AuthenticationFailedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtFilter extends OncePerRequestFilter  {

    private final JwtUtil jwtUtil;

    private static final String BEAR_PREFIX = "Bearer ";
    private static final int BEAR_PREFIX_LENGTH = BEAR_PREFIX.length();
    public static final String REQUEST_ATTRIBUTE_NAME = "memberId";

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/members/register", "/members/login"};
        String path = request.getRequestURI();
        return Arrays
                .stream(excludePath)
                .anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(BEAR_PREFIX)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "인증이 유효하지 않습니다.");
            return;
        }

        String token = authHeader.substring(BEAR_PREFIX_LENGTH);

        try {
            request.setAttribute(REQUEST_ATTRIBUTE_NAME, jwtUtil.extractId(token));
        } catch (AuthenticationFailedException exception) {
            response.sendError(HttpStatus.FORBIDDEN.value(), exception.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

}
