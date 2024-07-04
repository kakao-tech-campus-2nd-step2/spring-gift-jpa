package gift.main.service;

import gift.main.dto.UserDto;
import gift.main.dto.UserJoinRequest;
import gift.main.dto.UserLoginRequest;
import gift.main.entity.User;
import gift.main.global.validator.UserException;
import gift.main.repository.UserDao;
import gift.main.util.AuthUtil;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final UserDao userDao;
    private final AuthUtil authUtil;

    public MemberService(UserDao userDao, AuthUtil authUtil) {
        this.userDao = userDao;
        this.authUtil = authUtil;
    }

    public String joinUser(UserJoinRequest userJoinRequest) {
        //유효성 검사해야하는데용~!
        if (userDao.existsUserByEmail(userJoinRequest.email())) {
            throw new UserException("이미 존재하는 이메일 주소입니다.");
        }
        UserDto validUser = new UserDto(userJoinRequest) ;
        userDao.insertUser(validUser);
        return authUtil.createToken(validUser);
    }

    public String loginUser(UserLoginRequest userLoginRequest) {
        //유효성 검사해야하는데용~!
        if (!userDao.existsUserByEmail(userLoginRequest.email())) {
            throw new UserException("해당 이메일주소는 존재하지 않습니다.");
        }
        User user= userDao.existsUser(userLoginRequest.email(), userLoginRequest.password());
        if (user!=null) {
            throw new UserException("비밀번호가 틀렸습니다.");
        }
        return authUtil.createToken(user);
    }


}
