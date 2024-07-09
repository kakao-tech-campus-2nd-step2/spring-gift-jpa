package gift.service;

import gift.domain.AuthToken;
import gift.exception.EmailDuplicationException;
import gift.exception.UnAuthorizationException;
import gift.repository.token.TokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public String tokenSave(String token, String email){
        Optional<AuthToken> tokenByEmail = tokenRepository.findTokenByEmail(email);

        if(tokenByEmail.isPresent()){
            throw new EmailDuplicationException("이미 토큰을 발급한 아이디 입니다.");
        }

        AuthToken authToken = new AuthToken(token, email);
        tokenRepository.save(authToken);
        return token;
    }

    public AuthToken findToken(String token){
        AuthToken authToken = tokenRepository.findAuthTokenByToken(token)
                .orElseThrow(UnAuthorizationException::new);

        return authToken;
    }
}
