package gift.Service;

import gift.DTO.JwtToken;
import gift.DTO.UserEntity;
import gift.Exception.ForbiddenException;
import gift.Exception.UnauthorizedException;
import gift.Repository.UserDao;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserDao userDao;
  private final JwtService jwtService;

  public UserService(UserDao userDao, JwtService jwtService) {
    this.userDao = userDao;
    this.jwtService = jwtService;
  }

  public UserEntity userSignUp(UserEntity userInfo) {
    userDao.save(userInfo);
    return userInfo;
  }

  public JwtToken userLogin(UserEntity userInfo) {
    String email = userInfo.getEmail();
    String password = userInfo.getPassword();
    Optional<UserEntity> userByEmail = userDao.findByEmail(email);

    if (userByEmail == null) {
      throw new EmptyResultDataAccessException("해당 유저가 없습니다.", 1);
    }
    if (userInfo.matchLoginInfo(userByEmail)) {
      JwtToken jwtToken = jwtService.createAccessToken(userByEmail);
      if (jwtService.isValidToken(jwtToken)) { //토큰이 만료되었다면
        throw new UnauthorizedException("토큰이 유효하지 않습니다.");
      }
      return jwtToken;
    }
    throw new ForbiddenException("아이디 비밀번호가 틀립니다.");
  }
}
