package gift.domain;

import gift.exception.TokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtToken jwtToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header!= null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            try{
                String email = jwtToken.tokenToEmail(token);
                return true;
            }
            catch(Exception e){
                throw new TokenException("올바르지 않은 토큰");
            }

        }
        throw new TokenException("헤더에 올바른 인증 토큰이 필요합니다.(Bearer )");

    }

}