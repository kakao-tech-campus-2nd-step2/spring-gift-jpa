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
    System.out.println(userInfo);
    return loginService.UserSignUp(userInfo);
  }

  @PostMapping("/login/token")
  public ResponseEntity<JwtToken> userLogin(
    @RequestHeader("email") String email,
    @RequestHeader("pw") String pw) {
    JwtToken userToken = loginService.UserLogin(email, pw);
    if (userToken==null){
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(userToken);
  }
}
