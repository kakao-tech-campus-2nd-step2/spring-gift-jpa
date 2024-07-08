package gift.service;

import gift.dao.UserDao;
import gift.domain.User;
import gift.exception.DuplicateUserEmailException;
import gift.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;
    private final JwtUtil jwtUtil;

    public UserService(UserDao userDao, JwtUtil jwtUtil) {
        this.userDao = userDao;
        this.jwtUtil = jwtUtil;
    }

    public void generateUser(User user) {
        if(!userDao.userEmailCheck(user.getEmail())){
            throw new DuplicateUserEmailException(
                "UserEmail " + user.getEmail()+"already exists."
            );
        }
        userDao.signUp(user);

    }

    public String authenticateUser(User user) {
        return jwtUtil.generateToken(userDao.signIn(user));
    }


}
