package gift.service;

import gift.dto.TokenDto;
import gift.entity.Token;
import gift.repository.TokenRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class TokenService {
    private final TokenRepositoryInterface tokenRepositoryInterface;

    public TokenService(TokenRepositoryInterface tokenRepositoryInterface) {
        this.tokenRepositoryInterface = tokenRepositoryInterface;
    }

    public Token saveToken(Long userId) {
        Token newToken = makeTokenFrom(userId);
        return tokenRepositoryInterface.save(newToken);
    }

    public Token makeTokenFrom(Long userId) {

        String stringUserId = userId.toString();
        String tokenValue = "Basic " + Base64.getEncoder().encodeToString(stringUserId.getBytes());

        return new Token(tokenValue);
    }

    public String decodeTokenValue(String tokenValue) {
        byte[] decodedBytes = Base64.getDecoder().decode(tokenValue);
        return new String(decodedBytes);
    }

    public TokenDto getTokenDtoFrom(Long userId) {
        return TokenDto.fromEntity(tokenRepositoryInterface.getById(userId));
    }

    public void deleteTokenOf(Long userId) {
        Token newToken = makeTokenFrom(userId);
        tokenRepositoryInterface.delete(newToken);
    }

}
