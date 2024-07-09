package gift.member.controller;

import gift.member.model.Member;
import gift.member.dto.MemberDto;
import gift.member.service.MemberService;
import gift.member.service.TokenService;
import gift.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final ProductService productService; //

    public MemberController(MemberService memberService, ProductService productService) {
        this.memberService = memberService;
        this.productService = productService;
    }

    @PostMapping("/register") // 생성된 회원 정보로 JWT 토큰을 생성 -> 응답으로 반환
    public ResponseEntity<Map<String, String>> register(@RequestBody MemberDto memberDto) {
        // 회원 가입 처리
        Member member = memberService.register(memberDto.getEmail(), memberDto.getPassword());
        // 입력받은 정보로 부터 토큰 생성
        String token = TokenService.generateToken(member);
        // 응답으로 사용자 정보 맵 반환
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    // 로그인 결과 처리
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody MemberDto memberDto) {
        Member member = memberService.login(memberDto.getEmail(), memberDto.getPassword());
        String token = TokenService.generateToken(member);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

}