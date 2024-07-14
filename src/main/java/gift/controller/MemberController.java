package gift.controller;


import static gift.util.ResponseEntityUtil.responseError;

import gift.dto.JwtDTO;
import gift.dto.MemberDTO;
import gift.exception.BadRequestExceptions.EmailAlreadyHereException;
import gift.service.MemberService;
import gift.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Validated
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberController(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid MemberDTO memberDTO) {
        String token;
        try {
            memberService.register(memberDTO);
            token = jwtUtil.generateToken(memberDTO);
        } catch (RuntimeException e) {
            if(e instanceof EmailAlreadyHereException)
                return responseError(e, HttpStatus.CONFLICT);
            return responseError(e);
        }
        return new ResponseEntity<>(new JwtDTO(token), HttpStatus.OK);
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberDTO memberDTO) {
        String token;
        try {
            memberService.login(memberDTO);
            token = jwtUtil.generateToken(memberDTO);
        } catch (RuntimeException e) {
            return responseError(e, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new JwtDTO(token), HttpStatus.OK);
    }
}
