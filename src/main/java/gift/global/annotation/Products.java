package gift.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// ArgumentResolvers를 위해 만든 어노테이션.
// ForUser와 같은 네이밍이 괜찮은지 모르겠습니다.
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Products {

}
