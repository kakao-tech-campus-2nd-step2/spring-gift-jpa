package gift.api.member;

import gift.global.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid MemberRequest memberRequest) {
        if (memberRepository.existsByEmail(memberRequest.email())) {
            throw new EmailAlreadyExistsException();
        }
        Long id = memberRepository.save(new Member(
            memberRequest.email(), memberRequest.password(), memberRequest.role())).getId();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", JwtUtil.generateHeaderValue(
                JwtUtil.generateAccessToken(id, memberRequest.email(), memberRequest.role())));
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody MemberRequest memberRequest, @RequestHeader("Authorization") String token) {
        if (memberRepository.existsByEmailAndPassword(
                memberRequest.email(), memberRequest.password())) {
            var id = memberRepository.findByEmail(memberRequest.email()).get().getId();
            if (token.split(" ")[1].equals(
                JwtUtil.generateAccessToken(id, memberRequest.email(), memberRequest.role()))) {
                return ResponseEntity.ok().build();
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
