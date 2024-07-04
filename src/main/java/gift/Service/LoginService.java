package gift.Service;

import gift.DTO.JwtToken;
import gift.DTO.LoginDto;
import gift.Repository.LoginDao;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final LoginDao loginDao;
  private final JwtService jwtService;

  public LoginService(LoginDao loginDao, JwtService jwtService) {
    this.loginDao = loginDao;
    this.jwtService = jwtService;
  }

  public LoginDto UserSignUp(LoginDto userInfo) {
    loginDao.UserSignUp(userInfo);
    return userInfo;
  }

  public JwtToken UserLogin(String email, String pw) {
    LoginDto loginDto = loginDao.UserLogin(email, pw);
    JwtToken jwtToken = null;

    if (loginDto!=null){
      if (email.equals(loginDto.getEmail()) && pw.equals(loginDto.getPw())){
        jwtToken = jwtService.createAccessToken(loginDto);
      }
    }
    return jwtToken;
  }
}
