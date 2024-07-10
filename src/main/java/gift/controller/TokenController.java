package gift.controller;

import gift.service.TokenService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/token")
@RestController
public class TokenController {
    private TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/{userId}")
    public String makeTokenFrom(@RequestParam("userId") Long userId) {
        return tokenService.makeTokenFrom(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteTokenOf(@RequestParam("userId") Long userId) {
        tokenService.deleteTokenOf(userId);
    }
}
