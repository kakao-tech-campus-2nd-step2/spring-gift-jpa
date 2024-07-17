package gift.authentication.filter;

import gift.authentication.token.JwtResolver;
import gift.authentication.token.Token;
import gift.authentication.token.TokenContext;
import gift.web.validation.exception.client.InvalidCredentialsException;
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

        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if(Objects.nonNull(authorization) && authorization.startsWith(BEARER)) {
            String token = authorization.substring(BEARER.length());

            Long memberId = jwtResolver.resolveId(Token.from(token)).orElseThrow(InvalidCredentialsException::new);
            TokenContext.addCurrentMemberId(memberId);

            filterChain.doFilter(request, response);
            TokenContext.clear();
            return;
        }

        throw new InvalidCredentialsException();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return ignorePaths.contains(request.getRequestURI());
    }
}
