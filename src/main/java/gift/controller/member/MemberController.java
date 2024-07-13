package gift.controller.member;

import gift.annotation.PageInfo;
import gift.annotation.TokenEmail;
import gift.util.pagenation.PageInfoDTO;
import gift.dto.member.PwUpdateDTO;
import gift.dto.member.MemberResponseDTO;
import gift.exception.ForbiddenRequestException;
import gift.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<MemberResponseDTO>> getUsers(
            @PageInfo
            PageInfoDTO pageInfoDTO
    ) {
        return ResponseEntity.ok(memberService.getAllUsers(pageInfoDTO));
    }


    @DeleteMapping("/api/users")
    public ResponseEntity<Void> deleteUser(@TokenEmail String email) {
        memberService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/password-change")
    public ResponseEntity<String> updatePw(@TokenEmail String email, @RequestBody @Valid PwUpdateDTO pwUpdateDTO) {
        final boolean FORBIDDEN = true;

        if (FORBIDDEN) {
            throw new ForbiddenRequestException("password changing is not allowed");
        }

        memberService.updatePw(email, pwUpdateDTO);

        return ResponseEntity.ok("Password updated successfully");
    }
}
