package gift.member.controller;

import gift.global.response.ResultCode;
import gift.global.response.SimpleResultResponseDto;
import gift.global.utils.ResponseHelper;
import gift.member.dto.MemberRequestDTO;
import gift.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("")
    public ResponseEntity<SimpleResultResponseDto> createMember(@RequestBody MemberRequestDTO memberRequestDTO) {
        memberService.createMember(memberRequestDTO.toMemberServiceDto());
        return ResponseHelper.createSimpleResponse(ResultCode.CREATE_MEMBER_SUCCESS);
    }
}
