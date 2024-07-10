package gift.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * 관리자 페이지 기본 화면 연결을 위한 Controller
 */
@Controller
public class PageController {
    @GetMapping("/")
    public String start() {
        return "index.html";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

    @GetMapping("/register")
    public String register(){
        return "register.html";
    }

    @GetMapping("/wishList")
    public String wishList(){
        return "wishList.html";
    }

    @GetMapping("/adminProducts")
    public String manageProduct(){
        return "adminProduct.html";
    }

    @GetMapping("/adminUsers")
    public String manageUser(){
        return "adminUser.html";
    }
}
