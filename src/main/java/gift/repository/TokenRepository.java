package gift.repository;


import gift.domain.TokenAuth;

import java.util.Optional;

public interface TokenRepository {
    String save(String token, String email);

    Optional<TokenAuth> findTokenByToken(String substring);
}
