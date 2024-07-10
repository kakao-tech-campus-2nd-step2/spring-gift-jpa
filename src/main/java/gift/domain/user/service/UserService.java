package gift.domain.user.service;

import gift.auth.jwt.Token;
import gift.domain.user.dao.UserJpaRepository;
import gift.domain.user.dto.UserDto;
import gift.domain.user.dto.UserLoginDto;
import gift.domain.user.entity.Role;
import gift.domain.user.entity.User;
import gift.auth.jwt.JwtProvider;
import gift.exception.InvalidUserInfoException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final JwtProvider jwtProvider;

    public UserService(UserJpaRepository userJpaRepository, JwtProvider jwtProvider) {
        this.userJpaRepository = userJpaRepository;
        this.jwtProvider = jwtProvider;
    }

    public Token signUp(UserDto userDto) {
        User user = userDto.toUser();
        User savedUser = userJpaRepository.save(user);
        
        return jwtProvider.generateToken(savedUser);
    }

    public Token login(UserLoginDto userLoginDto) {
        User user = userJpaRepository.findByEmail(userLoginDto.email())
            .orElseThrow(() -> new InvalidUserInfoException("error.invalid.userinfo.email"));

        if (!user.checkPassword(userLoginDto.password())) {
            throw new InvalidUserInfoException("error.invalid.userinfo.password");
        }

        return jwtProvider.generateToken(user);
    }

    public Role verifyRole(Token token) {
        return jwtProvider.getAuthorization(token.token());
    }
}
