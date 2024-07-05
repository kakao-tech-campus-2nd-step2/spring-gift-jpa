package gift.Controller;

import gift.DTO.JwtToken;
import gift.DTO.LoginDto;
import gift.Service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class LoginController {

  private final LoginService loginService;

  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping("/signup")
  public LoginDto UserSignUp(@RequestBody LoginDto userInfo) {
    return loginService.UserSignUp(userInfo);
  }

  @PostMapping("/login")
  public ResponseEntity<JwtToken> userLogin(
    @RequestBody LoginDto userInfo) {
    String email = userInfo.getEmail();
    String pw = userInfo.getPw();
    return loginService.UserLogin(email,pw);
  }
}
