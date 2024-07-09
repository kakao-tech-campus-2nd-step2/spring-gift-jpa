package gift.controller.th;

import gift.service.UserListService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserListController {

    UserListService userListService;

    public UserListController(UserListService userListService) {
        this.userListService = userListService;
    }

    @GetMapping("/user/list/th")
    public ModelAndView thUserList() {
        ModelAndView mav = new ModelAndView("user-list");
        mav.addObject("userModel", userListService.getAll());
        return mav;
    }
}
