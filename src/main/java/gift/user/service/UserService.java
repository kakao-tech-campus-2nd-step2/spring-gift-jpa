package gift.user.service;

import gift.user.domain.User;
import gift.user.exception.UserAlreadyExistsException;
import gift.user.exception.UserNotFoundException;
import gift.user.persistence.UserRepository;
import gift.user.service.dto.UserInfoParams;
import gift.user.service.dto.UserSignInInfos;
import gift.user.service.dto.UserSignupInfos;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public UserService(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    public UserSignupInfos signUp(UserInfoParams userSignupRequest) {
        userRepository.findByUsername(userSignupRequest.username())
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException();
                });

        User newUser = new User(userSignupRequest.username(),
                PasswordProvider.encode(userSignupRequest.username(), userSignupRequest.password()));
        userRepository.save(newUser);

        User savedUser = userRepository.findByUsername(newUser.getUsername())
                .orElseThrow(UserNotFoundException::new);
        if (!PasswordProvider.match
                (userSignupRequest.username(), userSignupRequest.password(), savedUser.getPassword())
        ) {
            throw new UserNotFoundException();
        }

        String token = jwtProvider.generateToken(savedUser);

        return UserSignupInfos.of(savedUser.getId(), token);
    }

    public UserSignInInfos signIn(UserInfoParams userSignupRequest) {
        User savedUser = userRepository.findByUsername(userSignupRequest.username())
                .orElseThrow(UserNotFoundException::new);
        if (PasswordProvider.match(userSignupRequest.username(), userSignupRequest.password(),
                savedUser.getPassword())) {
            throw new UserNotFoundException();
        }

        String token = jwtProvider.generateToken(savedUser);

        return UserSignInInfos.of(token);
    }
}
