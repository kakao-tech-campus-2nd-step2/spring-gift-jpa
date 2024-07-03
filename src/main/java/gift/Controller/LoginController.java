package gift.Controller;

import gift.DTO.LoginDto;
import gift.Service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class LoginController {

  private final LoginService loginService;

  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping("/signUp")
  public LoginDto UserSignUp(@RequestBody LoginDto userInfo) {
    return loginService.UserSignUp(userInfo);
  }
}
