package gift.controller.token;

import gift.dto.Member;
import gift.dto.Token;
import gift.dto.response.TokenResponse;
import gift.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/auth/token")
    public ResponseEntity<TokenResponse> generateToken(@RequestBody Member member) {
        Token newToken = tokenService.generateToken(member.getId());
        return ResponseEntity.ok(new TokenResponse(newToken.getValue()));
    }

    //토큰 만료 정책: 매월 1일 자정에 모든 토큰 만료
    @Scheduled(cron = "0 0 0 1 * *")
    public void expireAllTokensMonthly() {
        tokenService.expireAll();
    }

}
