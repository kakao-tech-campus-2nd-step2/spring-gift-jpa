package gift.authentication.filter;

import static gift.web.validation.exception.code.ErrorCode.UNAUTHORIZED_INVALID_TOKEN;

import gift.utils.JsonUtils;
import gift.web.dto.response.ErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            ErrorResponse errorResponse = ErrorResponse.from(UNAUTHORIZED_INVALID_TOKEN);
            String errorResponseJson = JsonUtils.toJson(errorResponse);

            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(errorResponseJson);
        }
    }
}
