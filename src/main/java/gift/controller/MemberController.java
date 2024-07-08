package gift.controller;

import gift.model.dto.TokenRequestDto;
import gift.model.dto.TokenResponseDto;
import gift.repository.MemberDao;
import gift.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberDao memberDao;
    private final AuthService authService;

    public MemberController(MemberDao memberDao, AuthService authService) {
        this.memberDao = memberDao;
        this.authService = authService;
    }

    @PostMapping("/register")
    public TokenResponseDto register(
        @Valid @RequestBody TokenRequestDto tokenRequestDto) {
        memberDao.insertMember(tokenRequestDto.toEntity());
        return new TokenResponseDto(
            authService.getToken(tokenRequestDto));
    }

    @PostMapping("/login")
    public TokenResponseDto login(
        @Valid @RequestBody TokenRequestDto tokenRequestDto) {
        return new TokenResponseDto(
            authService.getToken(tokenRequestDto));
    }
}
