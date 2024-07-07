package gift.Controller;

import gift.DTO.JwtToken;
import gift.DTO.UserDto;
import gift.Service.JwtService;
import gift.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final JwtService jwtService;

  public UserController(UserService userService, JwtService jwtService) {
    this.userService = userService;
    this.jwtService = jwtService;
  }

  @PostMapping("/signup")
  public UserDto userSignUp(@Valid @RequestBody UserDto userInfo) {
    return userService.userSignUp(userInfo);
  }

  @PostMapping("/login")
  public ResponseEntity<JwtToken> userLogin(
    @Valid @RequestBody UserDto userInfo) {

    JwtToken jwtToken = userService.userLogin(userInfo);

    if (jwtToken == null) {
      return ResponseEntity.notFound().build();
    }
    if (jwtService.isValidToken(jwtToken)) {
      return ResponseEntity.ok(jwtToken);
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
