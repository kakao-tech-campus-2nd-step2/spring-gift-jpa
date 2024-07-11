package gift.main.aop;

import gift.main.Exception.CustomException;
import gift.main.Exception.ErrorCode;
import gift.main.annotation.SessionUser;
import gift.main.dto.UserVo;
import gift.main.entity.Role;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class AdminAspect {

    @Before("@within(gift.main.annotation.AdminCheck) || @annotation(gift.main.annotation.AdminCheck)")
    public void checkAdmin(@SessionUser UserVo userVo) {
        if (Role.ADMIN.equals(userVo.getRole())) {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }
    }
}
