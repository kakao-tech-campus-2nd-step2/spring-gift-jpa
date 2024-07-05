package gift.main.interceptor;

import gift.main.dto.UserVo;
import gift.main.global.Exception.TokenException;
import gift.main.util.AuthUtil;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthUtil authUtil;

    public AuthInterceptor(AuthUtil authUtil) {
        this.authUtil = authUtil;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("호출해주세요~!");
        //컨트롤러 호출 전 호출되는 메서드드드드...

        String authorization= request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new TokenException("헤더에 토큰을 넣어주세요.");
        }
        String token = authorization.split(" ")[1];
        String email = request.getHeader("email");
        String password = request.getHeader("password");

        // 출력문으로 확인합니다.
        System.out.println("token = " + token);
        System.out.println("email = " + email);
        System.out.println("password = " + password);

        if (token == null || email == null || password == null){
//            response.sendRedirect("/spring-gift/members/login");
            throw new TokenException("헤더에 이메일,비밀번호,토큰을 넣어주세요.");

        }

        System.out.println("호출은 되는겨");
        if (!authUtil.validateToken(token,email,password)) {
//            response.sendRedirect("/spring-gift/members/login");
            throw new TokenException("jwt토큰이 올바르지 않습니다.");
        }
        UserVo sessionUser = new UserVo(
                authUtil.getName(token),
                authUtil.getEmail(token),
                authUtil.getRole(token));

        HttpSession session = request.getSession(true);
        session.setAttribute("sessionUser",sessionUser);
        System.out.println("..??");
        return true;
    }

    @Override
    //컨트롤러 실행후 (예외 발생안해여!)
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    // 뷰 실행 후 (컨트롤러에서 발생 예외 여기로 전송된다링구 (링구먼지암?ㅋ 링커스 친구들임)
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HttpSession session = request.getSession(false);
        session.invalidate();
    }
}
