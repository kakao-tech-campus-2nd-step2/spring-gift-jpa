package gift.controller;

import gift.model.Member;
import gift.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody Member member) {
    String token = memberService.register(member);
    return ResponseEntity.ok(token);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody Member member) {
    String token = memberService.login(member.getEmail(), member.getPassword());
    return ResponseEntity.ok(token);
  }
}
