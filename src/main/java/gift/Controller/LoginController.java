package gift.Controller;

import gift.DTO.LoginDto;
import gift.Service.LoginService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
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

  @PostMapping("/signup")
  public LoginDto UserSignUp(@RequestBody LoginDto userInfo) {
    return loginService.UserSignUp(userInfo);
  }

  @PostMapping("/login/token")
  public ResponseEntity<Map<String, String>> userLogin(@RequestBody LoginDto userInfo) {
    LoginDto user = loginService.UserLogin(userInfo);
    if (user == null || !(userInfo.getUsername().equals(user.getUsername()) && userInfo.getPw()
      .equals(user.getPw()))) {
      return ResponseEntity.notFound().build();
    }
    String accessToken = user.getAccessToken();
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put("accessToken", accessToken);

    return ResponseEntity.ok(responseBody);
  }
}
