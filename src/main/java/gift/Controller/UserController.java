package gift.Controller;

import gift.DTO.JwtToken;
import gift.DTO.UserDto;
import gift.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signup")
  public UserDto UserSignUp(@RequestBody UserDto userInfo) {
    System.out.println(userInfo);
    return userService.UserSignUp(userInfo);
  }

  @PostMapping("/login/token")
  public ResponseEntity<JwtToken> userLogin(
    @RequestHeader("email") String email,
    @RequestHeader("pw") String pw) {
    return userService.UserLogin(email,pw);
  }
}
