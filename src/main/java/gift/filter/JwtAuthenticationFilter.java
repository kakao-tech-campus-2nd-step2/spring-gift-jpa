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

        // ë¡œê·¸???˜ì´ì§€?€ ?Œì› ê°€???˜ì´ì§€???„í„°ë§í•˜ì§€ ?ŠìŒ
        if (uri.startsWith("/members/login") || uri.startsWith("/members/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        // ?¸ì…˜???¬ìš©???•ë³´ê°€ ?ˆëŠ”ì§€ ?•ì¸
        Object member = request.getSession().getAttribute("member");
        if (member == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

