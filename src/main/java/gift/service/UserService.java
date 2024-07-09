package gift.service;

import gift.dao.UserDao;
import gift.dto.UserDTO;
import gift.entity.User;
import gift.exception.BadRequestExceptions.EmailAlreadyHereException;
import gift.exception.BadRequestExceptions.UserNotFoundException;
import gift.exception.InternalServerExceptions.DuplicatedUserException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void register(UserDTO userDto) throws RuntimeException {
        User user = new User(userDto);

        if (userDao.countUser(user.getEmail()) > 0) {
            throw new EmailAlreadyHereException("이미 있는 이메일입니다.");
        }

        userDao.insertUser(new User(userDto));
    }

    public void login(UserDTO userDto) throws RuntimeException {
        if (userDao.countUser(new User(userDto)) < 1)
            throw new UserNotFoundException("아이디 또는 비밀번호가 올바르지 않습니다.");

        if (userDao.countUser(new User(userDto)) > 1)
            throw new DuplicatedUserException(userDto.getEmail() + "is Duplicated in DB");

    }

    public UserDTO getUser(String email) throws RuntimeException {
        if (userDao.countUser(email) == 1)
            return UserConverter.convertToUserDTO(userDao.getUser(email));

        if (userDao.countUser(email) > 1)
            throw new DuplicatedUserException(email + "is Duplicated in DB");

        if(userDao.countUser(email) < 1)
            throw new UserNotFoundException(email + "을(를) 가지는 유저를 찾을 수 없습니다.");

        return null;
    }


}
