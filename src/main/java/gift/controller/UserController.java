package gift.controller;

import gift.dto.LoginDTO;
import gift.dto.UserDTO;
import gift.service.UserService;
import gift.util.JwtUtil;
import gift.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO("", "", ""));
        return "register_user_form";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute @Valid UserDTO userDTO, BindingResult result,
        Model model) {
        if (result.hasErrors()) {
            return "register_user_form";
        }

        User existingUser = userService.findUserByEmail(userDTO.email());
        if (existingUser != null) {
            model.addAttribute("emailError", "이메일이 이미 존재합니다.");
            return "register_user_form";
        }

        User user = new User(null, userDTO.name(), userDTO.email(), userDTO.password(), "user");
        userService.saveUser(userDTO);

        return "register_success";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDTO", new LoginDTO("", ""));
        return "login_user_form";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute @Valid LoginDTO loginDTO, BindingResult result,
        Model model) {
        if (result.hasErrors()) {
            return "login_user_form";
        }

        User user = userService.findUserByEmail(loginDTO.email());
        if (user == null || !user.password().equals(loginDTO.password())) {
            model.addAttribute("loginError", "잘못된 이메일 또는 비밀번호입니다.");
            return "login_user_form";
        }

        String token = jwtUtil.generateToken(user.email(), user.role());
        model.addAttribute("token", token);

        return "login_success";
    }
}