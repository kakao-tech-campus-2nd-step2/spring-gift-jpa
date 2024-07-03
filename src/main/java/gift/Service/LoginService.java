package gift.Service;

import gift.DTO.LoginDto;
import gift.Repository.LoginDao;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class LoginService {

  private final LoginDao loginDao;

  public LoginService(LoginDao loginDao) {
    this.loginDao = loginDao;
  }

  public LoginDto UserSignUp(@RequestBody LoginDto userInfo) {
    loginDao.UserSignUp(userInfo);
    return userInfo;
  }
}
