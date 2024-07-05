package gift.Service;

import gift.DTO.UserDto;
import gift.Repository.UserDao;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final UserDao userDao;

  public LoginService(UserDao userDao) {
    this.userDao = userDao;
  }

  public UserDto UserSignUp(UserDto userInfo) {
    userDao.UserSignUp(userInfo);
    return userInfo;
  }

  public UserDto UserLogin(UserDto userInfo) {
    String email = userInfo.getEmail();
    UserDto userDto = userDao.getUserByEmail(email);
    return userDto;
  }
}
