package gift.member.controller;

import gift.auth.token.AuthToken;
import gift.member.dto.MemberReqDto;
import gift.member.dto.MemberResDto;
import gift.member.service.MemberService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping
    public ResponseEntity<List<MemberResDto>> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());
    }

    @PostMapping("/register")
    public ResponseEntity<AuthToken> register(@RequestBody MemberReqDto memberReqDto) {
        return ResponseEntity.ok(memberService.register(memberReqDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResDto> getMember(@PathVariable("id") Long memberId) {
        return ResponseEntity.ok(memberService.getMember(memberId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMember(@PathVariable("id") Long memberId, @RequestBody MemberReqDto memberReqDto) {
        memberService.updateMember(memberId, memberReqDto);
        return ResponseEntity.ok("회원정보 수정 성공");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable("id") Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok("회원 삭제 성공");
    }
}
