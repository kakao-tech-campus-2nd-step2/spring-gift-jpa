package gift.Service;

import gift.DTO.JwtToken;
import gift.DTO.LoginDto;
import gift.Repository.UserDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final UserDao userDao;
  private final JwtService jwtService;

  public LoginService(UserDao userDao, JwtService jwtService) {
    this.userDao = userDao;
    this.jwtService = jwtService;
  }

  public LoginDto UserSignUp(LoginDto userInfo) {
    userDao.UserSignUp(userInfo);
    return userInfo;
  }

  public ResponseEntity<JwtToken> UserLogin(String email, String password) {
    LoginDto loginDto = userDao.getUserByEmail(email);
    JwtToken jwtToken;

    if (loginDto==null){
      return ResponseEntity.notFound().build();
    }
    if (email.equals(loginDto.getEmail()) && password.equals(loginDto.getPassword())){
      jwtToken = jwtService.createAccessToken(loginDto);
      if (jwtService.isValidToken(jwtToken)){
        return ResponseEntity.ok(jwtToken);
      }
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }
}
