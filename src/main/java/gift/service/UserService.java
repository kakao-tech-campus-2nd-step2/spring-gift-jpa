package gift.service;

import gift.exception.user.UserNotFoundException;
import gift.model.User;
import gift.repository.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserByEmail(String email) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("해당 email의 계정이 존재하지 않습니다."));
        return user;
    }

    public User getUserById(Long id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new UserNotFoundException("해당 email의 계정이 존재하지 않습니다."));
        return user;
    }
}
