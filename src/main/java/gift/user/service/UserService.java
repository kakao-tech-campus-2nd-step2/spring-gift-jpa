package gift.user.service;

import gift.user.application.dto.request.UserSignUpRequest;
import gift.user.application.dto.response.UserSignInResponse;
import gift.user.domain.User;
import gift.user.exception.UserAlreadyExistsException;
import gift.user.exception.UserNotFoundException;
import gift.user.persistence.UserRepository;
import gift.user.service.dto.UserSignInDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public UserService(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    public UserSignInDto signUp(UserSignUpRequest userSignupRequest) {
        User user = userSignupRequest.toModel();

        userRepository.findByUsername(user.getUsername())
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException();
                });

        userRepository.save(user);
        User savedUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(UserNotFoundException::new);
        if (PasswordProvider.match(userSignupRequest.username(), userSignupRequest.password(),
                savedUser.getPassword())) {
            throw new UserNotFoundException();
        }

        String token = jwtProvider.generateToken(savedUser);

        return new UserSignInDto(savedUser.getId(), token);
    }

    public UserSignInResponse signIn(UserSignUpRequest userSignupRequest) {
        User savedUser = userRepository.findByUsername(userSignupRequest.username())
                .orElseThrow(UserNotFoundException::new);
        if (PasswordProvider.match(userSignupRequest.username(), userSignupRequest.password(),
                savedUser.getPassword())) {
            throw new UserNotFoundException();
        }

        String token = jwtProvider.generateToken(savedUser);

        return new UserSignInResponse(token);
    }
}
