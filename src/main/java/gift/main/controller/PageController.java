package gift.main.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/admin/product")

    public String adminPage() {
        return "product";
    }

    @GetMapping({"/members/login", "/members/register"})
    public String membersPage() {

        return "user";

    }


}
