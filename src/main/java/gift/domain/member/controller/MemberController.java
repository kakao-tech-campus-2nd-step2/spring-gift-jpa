package gift.domain.member.controller;

import gift.domain.member.dto.MemberRequest;
import gift.domain.member.dto.MemberResponse;
import gift.domain.member.service.MemberService;
import gift.util.dto.JwtResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping()
    public ResponseEntity<List<MemberResponse>> getAllMember() {
        List<MemberResponse> memberList = memberService.getAllMember();
        return new ResponseEntity<>(memberList, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody MemberRequest memberRequest) {
        String token = memberService.register(memberRequest);

        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody MemberRequest memberRequest) {

        String token = memberService.login(memberRequest);

        if (token != null) {
            return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}