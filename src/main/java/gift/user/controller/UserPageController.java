package gift.user.controller;


import gift.user.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserPageController {

  @GetMapping("/register")
  public String showRegisterationPage(Model model){
    model.addAttribute("userDto", new UserDto());
    return "user/register";
  }

  @GetMapping("/login")
  public String showLoginPage(Model model) {
    model.addAttribute("userDto", new UserDto());
    return "user/login";
  }



}
