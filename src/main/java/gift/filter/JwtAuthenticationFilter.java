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
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        String uri = request.getRequestURI();

        // 로그???�이지?� ?�원 가???�이지???�터링하지 ?�음
        if (uri.startsWith("/members/login") || uri.startsWith("/members/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        // ?�션???�용???�보가 ?�는지 ?�인
        Object member = request.getSession().getAttribute("member");
        if (member == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

