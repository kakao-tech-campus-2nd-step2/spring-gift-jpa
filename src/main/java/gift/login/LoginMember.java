package gift.login;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)  // 파라미터에 사용할 수 있도록 설정
@Retention(RetentionPolicy.RUNTIME)  // 런타임까지 어노테이션 정보 유지
public @interface LoginMember {
}
