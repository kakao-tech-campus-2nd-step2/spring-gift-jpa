package gift.Controller;

import gift.Model.User;
import gift.Service.UserService;
import gift.Utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute User user, Model model, HttpServletResponse response) {
        String email = user.getEmail();
        String password = user.getPassword();

        boolean isAuthenticated = userService.authenticate(email, password);
        if (isAuthenticated) {
            boolean isAdmin = userService.isAdmin(email);
            User authenticatedUser = userService.findByEmail(email);
            String token = jwtUtil.generateToken(authenticatedUser, isAdmin);
            // Set token in HttpOnly cookie
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            if (isAdmin) {
                return "redirect:/api/products";
            }
            return "redirect:/products";
        }

        model.addAttribute("error", "Authentication failed");
        return "login";
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST})
    public String register(@ModelAttribute User user, Model model, HttpServletRequest request) {
        if("GET".equalsIgnoreCase(request.getMethod())) {
            model.addAttribute("user", new User());
            return "register";
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            model.addAttribute("user", user);
            userService.register(user);
            model.addAttribute("message", "회원가입에 성공했습니다.");
            return "login";
        }
        return "login";
    }
}
