package gift.controller.th;

import gift.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserListController {

    UserService userService;

    public UserListController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/list/th")
    public ModelAndView thUserList() {
        ModelAndView mav = new ModelAndView("user-list");
        mav.addObject("userModel", userService.getAll());
        return mav;
    }
}
