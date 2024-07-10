package gift.service;

import gift.entity.Token;
import gift.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class TokenService {
    private TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token makeTokenFrom(Long userId) {

        String stringUserId = userId.toString();
        String tokenValue =  Base64.getEncoder().encodeToString(stringUserId.getBytes());
        Token newToken = new Token(tokenValue);

        Long newId = tokenRepository.save(newToken);

        return new Token(newId,tokenValue);
    }

    public void deleteTokenOf(Long userId) {

        Token newToken = makeTokenFrom(userId);
        tokenRepository.delete(newToken);
    }

}
