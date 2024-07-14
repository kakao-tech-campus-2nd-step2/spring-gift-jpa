package gift.Controller;

import gift.Model.Member;
import gift.Model.MemberAccessToken;
import gift.Service.MemberAccessTokenProvider;
import gift.Service.MemberService;

import jakarta.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    private final MemberService memberService;
    private final MemberAccessTokenProvider memberAccessTokenProvider;

    public MemberController(MemberService memberService, MemberAccessTokenProvider memberAccessTokenProvider){
        this.memberService = memberService;
        this.memberAccessTokenProvider  = memberAccessTokenProvider;
    }

    @GetMapping("/api/login")
    public String getLogin(Model model) {
        model.addAttribute("member",new Member(0L,"",""));
        return "login";
    }
    @GetMapping("/api/signup")
    public String getSignup(Model model) {
        model.addAttribute("member",new Member(0L,"",""));
        return "signup";
    }

    @GetMapping("/api/token/{email}")
    public String getToken(@PathVariable(value = "email") String email, Model model) {
        String token = memberAccessTokenProvider.createJwt(email);
        model.addAttribute("token", new MemberAccessToken(token));
        return "token";// 토큰을 localStroage에 저장 후 header로 보냄
    }

    @PostMapping("/api/signup")
    public String signupMember(@Valid@ModelAttribute Member member) {
        memberService.signupMember(member);
        return "redirect:/api/login";
    }

    @PostMapping("/api/login/check")
    public String checkMember(@Valid@ModelAttribute Member member) {
        Member checkMember;
        try {
            checkMember = memberService.getMemberByEmail(member.getEmail());
        }catch (EmptyResultDataAccessException e){
            throw new IllegalArgumentException();
        }
        if(checkMember.getPassword().equals(member.getPassword())){
            return "redirect:/api/token/" + member.getEmail();
        }
        return "redirect:/api/login";
    }
}
