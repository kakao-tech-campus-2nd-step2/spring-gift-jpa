package gift.Service;

import gift.DTO.LoginDto;
import gift.Repository.LoginDao;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final LoginDao loginDao;

  public LoginService(LoginDao loginDao) {
    this.loginDao = loginDao;
  }

  public LoginDto UserSignUp(LoginDto userInfo) {
    loginDao.UserSignUp(userInfo);
    return userInfo;
  }

  public LoginDto UserLogin(LoginDto userInfo) {
    return loginDao.UserLogin(userInfo);
  }
}
