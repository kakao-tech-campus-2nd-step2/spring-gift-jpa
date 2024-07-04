package gift.main.interceptor;

import gift.main.dto.UserVo;
import gift.main.util.AuthUtil;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthLoginInterceptor implements HandlerInterceptor {

    private final AuthUtil authUtil;

    public AuthLoginInterceptor(AuthUtil authUtil) {
        this.authUtil = authUtil;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //컨트롤러 호출 전 호출되는 메서드드드드...
        String token = request.getHeader("Authorization");
        String email = request.getHeader("email");
        String password = request.getHeader("password");
        if (!authUtil.validateToken(token,email,password)) {
            throw new AuthException();
        }
        UserVo sessionUser = new UserVo(
                authUtil.getName(token),
                authUtil.getEmail(token),
                authUtil.getRole(token));

        HttpSession session = request.getSession(false);
        session.setAttribute("sessionUser",sessionUser);
        return true;
    }

    @Override
    //컨트롤러 실행후
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    // 뷰 실행 후 (컨트롤러에서 발생 예외 여기로 전송된다링구 (링구먼지암?ㅋ 링커스 친구들임)
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
