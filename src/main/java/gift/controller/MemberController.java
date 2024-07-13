package gift.controller;

import gift.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/members")
public class MemberController {

  private final MemberService memberService;


  @Autowired
  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody MemberRequest memberRequest) {
    String token = memberService.register(memberRequest.getEmail(), memberRequest.getPassword());
    return ResponseEntity.ok(new TokenResponse(token));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody MemberRequest memberRequest) {
    String token = memberService.authenticate(memberRequest.getEmail(), memberRequest.getPassword());
    return ResponseEntity.ok(new TokenResponse(token));
  }
}

