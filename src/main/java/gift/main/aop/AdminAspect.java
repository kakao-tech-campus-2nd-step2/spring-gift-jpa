package gift.main.aop;

import gift.main.Exception.CustomException;
import gift.main.Exception.ErrorCode;
import gift.main.annotation.SessionUser;
import gift.main.dto.UserVo;
import gift.main.entity.Role;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;



@Component
@Aspect
public class AdminAspect {

    
    @Pointcut("execution(* gift.main.controller.ProductController.*(..))")
    public void cut() {

    }

    @Before("cut()")
    public void checkAdmin(@SessionUser UserVo userVo) {
        System.out.println("실행된다.");
        if (!Role.ADMIN.equals(userVo.getRole())) {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }
    }
}
