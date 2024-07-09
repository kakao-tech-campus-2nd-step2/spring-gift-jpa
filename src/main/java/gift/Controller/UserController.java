package gift.Controller;

import gift.Model.ResponseLoginDTO;
import gift.Model.User;
import gift.Service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user){
        userService.signUpUser(user);
        return "signUp successed";
    }


    @PostMapping("/login")
    public ResponseLoginDTO loginUser(@RequestBody User user) {
        return new ResponseLoginDTO(userService.loginUser(user));
    }

}
