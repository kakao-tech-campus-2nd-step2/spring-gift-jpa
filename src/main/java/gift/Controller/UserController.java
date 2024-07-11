package gift.Controller;

import gift.DTO.JwtToken;
import gift.DTO.UserEntity;
import gift.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signup")
  public ResponseEntity<UserEntity> userSignUp(@Valid @RequestBody UserEntity userInfo) {
    UserEntity userEntity = userService.userSignUp(userInfo);
    return ResponseEntity.ok(userEntity);
  }

  @PostMapping("/login")
  public ResponseEntity<JwtToken> userLogin(
    @Valid @RequestBody UserEntity userInfo) {
    JwtToken jwtToken = userService.userLogin(userInfo);
    return ResponseEntity.ok(jwtToken);
  }
}
