package gift.Service;

import gift.DTO.JwtToken;
import gift.DTO.UserDto;
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

  public UserDto userSignUp(UserDto userInfo) {
    UserEntity userEntity = new UserEntity(userInfo.getId(), userInfo.getEmail(),
      userInfo.getPassword());
    userDao.save(userEntity);
    return userInfo;
  }

  public JwtToken userLogin(UserDto userInfo) {
    String email = userInfo.getEmail();
    Optional<UserEntity> userByEmail = userDao.findByEmail(email);

    if (userByEmail == null) {
      throw new EmptyResultDataAccessException("해당 유저가 없습니다.", 1);
    }
    if (userByEmail.get().matchLoginInfo(userInfo)) {
      JwtToken jwtToken = jwtService.createAccessToken(userByEmail);
      if (jwtService.isValidToken(jwtToken)) { //토큰이 만료되었다면
        throw new UnauthorizedException("토큰이 유효하지 않습니다.");
      }
      return jwtToken;
    }
    throw new ForbiddenException("아이디 비밀번호가 틀립니다.");
  }
}
