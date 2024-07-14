package gift.user.controller;

import gift.user.dto.UserDto;
import gift.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody UserDto userDto) {
    String token = userService.register(userDto);
    return ResponseEntity.ok(new TokenResponse(token));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody UserDto userDto) {
    String token = userService.authenticate(userDto.getEmail(), userDto.getPassword());
    return ResponseEntity.ok(new TokenResponse(token));
  }
}

