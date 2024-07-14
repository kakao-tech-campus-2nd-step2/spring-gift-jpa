package gift.controller;

import gift.dto.MemberRequest;
import gift.dto.MemberResponse;
import gift.service.MemberService;
import gift.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberController(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("memberRequest", new MemberRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute MemberRequest memberRequest, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        try {
            String token = memberService.authenticate(memberRequest);
            session.setAttribute("token", token);
            return "redirect:/wishes/items";
        } catch (IllegalArgumentException e) {
            return "redirect:/members/login?error";
        }
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("memberRequest", new MemberRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute MemberRequest memberRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            String token = memberService.register(memberRequest);
            return "redirect:/members/login?registerSuccess";
        } catch (IllegalArgumentException e) {
            bindingResult.reject("error.register", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            Claims claims = jwtUtil.extractClaims(token.replace("Bearer ", ""));
            Long memberId = Long.parseLong(claims.getSubject());
            return ResponseEntity.ok("Token is valid for member ID: " + memberId);
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Invalid token");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMemberById(@PathVariable Long id) {
        MemberResponse memberResponse = memberService.findById(id);
        return ResponseEntity.ok(memberResponse);
    }
}
