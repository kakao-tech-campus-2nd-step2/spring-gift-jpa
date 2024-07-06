package gift.main.service;

import gift.main.dto.UserDto;
import gift.main.dto.UserJoinRequest;
import gift.main.dto.UserLoginRequest;
import gift.main.entity.User;
import gift.main.global.Exception.ErrorCode;
import gift.main.global.Exception.UserException;
import gift.main.repository.UserDao;
import gift.main.util.AuthUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;
    private final AuthUtil authUtil;

    public UserService(UserDao userDao, AuthUtil authUtil) {
        this.userDao = userDao;
        this.authUtil = authUtil;
    }

    public String joinUser(UserJoinRequest userJoinRequest) {
        //유효성 검사해야하는데용~!
        if (userDao.existsUserByEmail(userJoinRequest.email())) {
            throw new UserException(ErrorCode.ALREADY_EMAIL.getErrorMessage(), ErrorCode.ALREADY_EMAIL.getHttpStatus());
        }
        UserDto userDto = new UserDto(userJoinRequest) ;
        Long id = userDao.insertUser(userDto);
        String token = authUtil.createToken(id,userDto);
        return token;

    }

    public String loginUser(UserLoginRequest userLoginRequest) {
        //유효성 검사해야하는데용~!
        if (!userDao.existsUserByEmail(userLoginRequest.email())) {
            throw new UserException(ErrorCode.ERROR_LOGIN.getErrorMessage(), ErrorCode.ERROR_LOGIN.getHttpStatus());
        }

        User user= userDao.existsUser(userLoginRequest.email(), userLoginRequest.password());
        if (user==null) {
            throw new UserException(ErrorCode.ERROR_LOGIN.getErrorMessage(), ErrorCode.ERROR_LOGIN.getHttpStatus());
        }
        return authUtil.createToken(user);
    }


}
