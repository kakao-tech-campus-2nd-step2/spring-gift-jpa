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

        // λ‘κ·Έ???μ΄μ§? ?μ κ°???μ΄μ§???ν°λ§νμ§ ?μ
        if (uri.startsWith("/members/login") || uri.startsWith("/members/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        // ?Έμ???¬μ©???λ³΄κ° ?λμ§ ?μΈ
        Object member = request.getSession().getAttribute("member");
        if (member == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

