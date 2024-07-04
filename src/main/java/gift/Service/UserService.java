package gift.Service;

import gift.DTO.JwtToken;
import gift.DTO.UserDto;
import gift.Repository.UserDao;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserDao userDao;
  private final JwtService jwtService;

  public UserService(UserDao userDao, JwtService jwtService) {
    this.userDao = userDao;
    this.jwtService = jwtService;
  }

  public UserDto UserSignUp(UserDto userInfo) {
    userDao.UserSignUp(userInfo);
    return userInfo;
  }

  public ResponseEntity<JwtToken> UserLogin(String email, String pw) {
    UserDto userDto = userDao.UserLogin(email, pw);
    JwtToken jwtToken = null;

    if (userDto ==null){
      return ResponseEntity.notFound().build();
    }
    if (email.equals(userDto.getEmail()) && pw.equals(userDto.getPw())){
      jwtToken = jwtService.createAccessToken(userDto);
      if (jwtService.isValidToken(jwtToken)){
        return ResponseEntity.ok(jwtToken);
      }
      return ResponseEntity.status(401).build();
    }
    return ResponseEntity.status(403).build();
  }
}
