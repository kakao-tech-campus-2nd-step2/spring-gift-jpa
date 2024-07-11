package gift.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.UserDTO;
import gift.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/signup")
    public String signUp() {
        return "user/signup";
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/signup")
    public String SignUp(@RequestBody UserDTO.SignUpDTO signUpDTO) {
        userService.signUp(signUpDTO);
        return "redirect:/signin";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/signin")

    public String signIn() {
        return "user/signin";
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/signin")
    @ResponseBody
    public String signIn(@RequestBody UserDTO.LoginDTO loginDTO) throws JsonProcessingException {
        UserDTO.Token token = userService.signIn(loginDTO);
        return objectMapper.writeValueAsString(token);
    }


}
