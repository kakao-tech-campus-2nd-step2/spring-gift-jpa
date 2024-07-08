package gift.controller.product;

import gift.auth.Authorization;
import gift.model.user.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductPageController {

    @Authorization(role = Role.ADMIN)
    @GetMapping("/admin")
    public String admin() {
        return "adminPage";
    }

}
