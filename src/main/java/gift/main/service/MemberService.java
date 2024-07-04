package gift.main.service;

import gift.main.dto.UserDto;
import gift.main.dto.UserJoinRequest;
import gift.main.global.validator.AlreadyExistsException;
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

    public String joinUser(UserJoinRequest userJoinDto) {
        //유효성 검사해야하는데용~!
        if (userDao.existsUserByEmail(userJoinDto.email())) {
            throw new AlreadyExistsException("이미 존재하는 회원");
        }
        UserDto validUser = new UserDto(userJoinDto) ;
        userDao.insertUser(validUser);
        return authUtil.createToken(validUser);
    }
}
