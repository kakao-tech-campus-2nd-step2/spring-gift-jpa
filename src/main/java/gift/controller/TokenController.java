package gift.controller;

import gift.service.TokenService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/token")
@RestController
public class TokenController {
    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/{userId}")
    public String makeTokenFrom(@RequestParam("userId") Long userId) {
        return tokenService.makeTokenFrom(userId).getTokenValue();
    }

    @DeleteMapping("/{userId}")
    public void deleteTokenOf(@RequestParam("userId") Long userId) {
        tokenService.deleteTokenOf(userId);
    }
}
