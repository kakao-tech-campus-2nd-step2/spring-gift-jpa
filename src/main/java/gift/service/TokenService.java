package gift.service;

import gift.dto.Token;
import gift.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class TokenService {

    protected static SecureRandom random = new SecureRandom();
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public synchronized Token generateToken(Long registeredMemberId) {
        long longToken = Math.abs(random.nextLong());
        String random = Long.toString(longToken, 16);
        Token createdToken = new Token(registeredMemberId, random);
        tokenRepository.saveToken(createdToken);
        return new Token(registeredMemberId, random);
    }

    public Token getToken(Long registeredMemberId) {
        return tokenRepository.getTokenByMemberId(registeredMemberId);
    }

    public boolean isValidateToken(String token) {
        return tokenRepository.containsToken(token);
    }

    public Long getMemberIdByToken(String token) {
        return tokenRepository.getMemberIdByToken(token);
    }

    public void expireAll() {
        tokenRepository.deleteAll();
    }

}
