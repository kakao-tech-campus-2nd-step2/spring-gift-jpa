package gift.view;

import gift.controller.UserController;
import gift.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/users")
public class AuthViewController {
    private final UserController userController;

    public AuthViewController(UserController userController) {
        this.userController = userController;
    }

    @GetMapping("/new")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User(0L, "", ""));
        return "signup";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute User user) {
        userController.createUser(user);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User(0L, "", ""));
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model) {
        ResponseEntity<Map<String, String>> response = userController.loginByEmailPassword(user);
        String token = response.getBody().get("token");
        model.addAttribute("token", token);
        return "redirect:/wishlist/" + user.getId();
    }
}
