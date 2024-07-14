package gift.Controller;

import gift.Entity.Member;
import gift.Model.MemberDto;
import gift.Service.MemberService;
import gift.Utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberController(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new MemberDto());
        return "login";
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute MemberDto memberDto, Model model, HttpServletResponse response) {
        String email = memberDto.getEmail();
        String password = memberDto.getPassword();

        boolean isAuthenticated = memberService.authenticate(email, password);
        if (isAuthenticated) {
            boolean isAdmin = memberService.isAdmin(email);
            MemberDto authenticatedMember = memberService.findByEmail(email);
            String token = jwtUtil.generateToken(authenticatedMember, isAdmin);
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
    public String register(@ModelAttribute MemberDto memberDto, Model model, HttpServletRequest request) {
        if("GET".equalsIgnoreCase(request.getMethod())) {
            model.addAttribute("user", new MemberDto());
            return "register";
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            model.addAttribute("user", memberDto);
            memberService.register(memberDto);
            model.addAttribute("message", "회원가입에 성공했습니다.");
            return "login";
        }
        return "login";
    }
}
