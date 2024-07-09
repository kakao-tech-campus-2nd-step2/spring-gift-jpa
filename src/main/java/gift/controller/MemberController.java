package gift.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gift.dto.MemberDto;
import gift.dto.request.LoginRequest;
import gift.service.MemberService;
import gift.util.JwtUtil;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/members")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService, JwtUtil jwtUtil){
        this.memberService = memberService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody MemberDto memberDto, BindingResult bindingResult){
        memberService.addMember(memberDto);
        String token = memberService.generateToken(memberDto.getEmail());
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){
        String token = memberService.authenticateMember(loginRequest);
        return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
    }
}
