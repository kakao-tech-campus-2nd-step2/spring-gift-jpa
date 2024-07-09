package gift.authentication.filter;

import gift.authentication.token.JwtResolver;
import gift.authentication.token.Token;
import gift.web.validation.exception.InvalidCredentialsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter {

    private final List<String> ignorePaths = List.of("/api/members/login", "/api/members/register");
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER = "Bearer ";
    private final JwtResolver jwtResolver;

    public AuthenticationFilter(JwtResolver jwtResolver) {
        this.jwtResolver = jwtResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        //검증이 필요없는 요청
        if (ignorePaths.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        //검증이 필요한 요청
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if(Objects.nonNull(authorization) && authorization.startsWith(BEARER)) {
            String token = authorization.substring(BEARER.length());
            jwtResolver.resolve(Token.from(token));

            filterChain.doFilter(request, response);
            return;
        }

        throw new InvalidCredentialsException();
    }
}
