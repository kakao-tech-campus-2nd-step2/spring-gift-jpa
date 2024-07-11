package gift.Controller;

import gift.DTO.JwtToken;
import gift.DTO.UserDto;
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
  public ResponseEntity<UserDto> userSignUp(@Valid @RequestBody UserDto userDtoInfo) {
    UserDto userDto = userService.userSignUp(userDtoInfo);
    return ResponseEntity.ok(userDto);
  }

  @PostMapping("/login")
  public ResponseEntity<JwtToken> userLogin(
    @Valid @RequestBody UserDto userDtoInfo) {
    JwtToken jwtToken = userService.userLogin(userDtoInfo);
    return ResponseEntity.ok(jwtToken);
  }
}
