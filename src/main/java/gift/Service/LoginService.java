package gift.Service;

import gift.DTO.JwtToken;
import gift.DTO.LoginDto;
import gift.Repository.LoginDao;
import org.springframework.http.ResponseEntity;
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

  public ResponseEntity<JwtToken> UserLogin(String email, String password) {
    LoginDto loginDto = loginDao.getUserByEmail(email);
    JwtToken jwtToken;

    if (loginDto==null){
      return ResponseEntity.notFound().build();
    }
    if (email.equals(loginDto.getEmail()) && password.equals(loginDto.getPassword())){
      jwtToken = jwtService.createAccessToken(loginDto);
      if (jwtService.isValidToken(jwtToken)){
        return ResponseEntity.ok(jwtToken);
      }
      return ResponseEntity.status(401).build();
    }
    return ResponseEntity.status(403).build();
  }
}
