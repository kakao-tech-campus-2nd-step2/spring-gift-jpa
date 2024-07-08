package gift.reflection;

import gift.controller.auth.AuthInterceptor;
import gift.model.MemberRole;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class AuthTestReflectionComponent {

    private AuthInterceptor authInterceptor;

    public AuthTestReflectionComponent(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    public Long getMemberIdWithToken(String token) {
        try {
            Method method = authInterceptor.getClass().getDeclaredMethod("getMemberIdWithToken", String.class);
            method.setAccessible(true);
            Long memberId = (Long) method.invoke(authInterceptor, token);
            return memberId;
        } catch (Exception e) {
            throw new RuntimeException("토큰으로 ID 복호화하는 과정에서 예외 발생: " + e.getMessage(), e);
        }
    }

    public MemberRole getMemberRoleWithToken(String token) {
        try {
            Method method = AuthInterceptor.class.getDeclaredMethod("getMemberRoleWithToken", String.class);
            method.setAccessible(true);
            MemberRole memberRole = (MemberRole) method.invoke(authInterceptor, token);
            return memberRole;
        } catch (Exception e) {
            throw new RuntimeException("토큰으로 ROLE 복호화하는 과정에서 예외 발생: " + e.getMessage(), e);
        }
    }
}
