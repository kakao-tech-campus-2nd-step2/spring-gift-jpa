package gift.Service;

import gift.DTO.LoginDto;
import gift.Repository.UserDao;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final UserDao userDao;

  public LoginService(UserDao userDao) {
    this.userDao = userDao;
  }

  public LoginDto UserSignUp(LoginDto userInfo) {
    userDao.UserSignUp(userInfo);
    return userInfo;
  }

  public LoginDto UserLogin(LoginDto userInfo) {
    String email = userInfo.getEmail();
    LoginDto loginDto = userDao.getUserByEmail(email);
    return loginDto;
  }
}
