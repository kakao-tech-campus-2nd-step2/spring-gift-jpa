package gift.Service;

import gift.DTO.UserDto;
import gift.Repository.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public UserDto userSignUp(UserDto userInfo) {
    userDao.createUser(userInfo);
    return userInfo;
  }

  public UserDto userLogin(UserDto userInfo) {
    String email = userInfo.getEmail();
    UserDto userDto = userDao.getUserByEmail(email);
    return userDto;
  }
}
