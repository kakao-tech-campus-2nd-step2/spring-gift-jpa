package gift.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        // 로그인 페이지와 회원 가입 페이지는 필터링하지 않음
        if (uri.startsWith("/members/login") || uri.startsWith("/members/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 세션에 사용자 정보가 있는지 확인
        Object member = request.getSession().getAttribute("member");
        if (member == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
