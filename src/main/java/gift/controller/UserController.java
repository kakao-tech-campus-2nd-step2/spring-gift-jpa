package gift.controller;

import gift.dto.LoginUser;
import gift.dto.UserDTO;
import gift.entity.User;
import gift.service.LoginMember;
import gift.service.UserService;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signupRendering() {
        return "signup";
    }

    @GetMapping("/login")
    public String loginRendering() {
        return "login";
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> register(@ModelAttribute("userDTO") UserDTO userDTO) {
        String token = userService.signUp(userDTO);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("token", token);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body(responseBody);
    }

    @PostMapping("/login")
    public  ResponseEntity<Map<String, String>> login(@ModelAttribute("userDTO") UserDTO userDTO) {
        String token = userService.login(userDTO);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("token", token);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body(responseBody);
    }

    @PostMapping("/token-login")
    public ResponseEntity<String> tokenLogin(@LoginMember LoginUser loginUser) {
        userService.tokenLogin(loginUser);
        String token = loginUser.getToken();
        return new ResponseEntity<>(token , HttpStatus.OK);
    }

    @Description("임시 확인용 html form. service x ")
    @GetMapping("/user-info")
    public ResponseEntity<List<User>> userInfoRendering() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

}
