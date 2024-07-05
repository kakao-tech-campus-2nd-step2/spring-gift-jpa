package gift.Controller;

import gift.DTO.JwtToken;
import gift.DTO.LoginDto;
import gift.Service.JwtService;
import gift.Service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class LoginController {

  private final LoginService loginService;
  private final JwtService jwtService;

  public LoginController(LoginService loginService, JwtService jwtService) {
    this.loginService = loginService;
    this.jwtService=jwtService;
  }

  @PostMapping("/signup")
  public LoginDto UserSignUp(@Valid @RequestBody LoginDto userInfo) {
    return loginService.UserSignUp(userInfo);
  }

  @PostMapping("/login")
  public ResponseEntity<JwtToken> userLogin(
    @Valid @RequestBody LoginDto userInfo) {
    String email = userInfo.getEmail();
    String password = userInfo.getPassword();

    LoginDto loginDto = loginService.UserLogin(email, password);
    JwtToken jwtToken;

    if (loginDto == null) {
      return ResponseEntity.notFound().build();
    }
    if (email.equals(loginDto.getEmail()) && password.equals(loginDto.getPassword())) {
      jwtToken = jwtService.createAccessToken(loginDto);
      if (jwtService.isValidToken(jwtToken)) {
        return ResponseEntity.ok(jwtToken);
      }
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }
}
