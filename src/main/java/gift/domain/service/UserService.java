package gift.domain.service;

import gift.domain.dto.request.UserRequest;
import gift.domain.dto.response.UserResponse;
import gift.domain.entity.User;
import gift.domain.exception.UserAlreadyExistsException;
import gift.domain.exception.UserIncorrectLoginInfoException;
import gift.domain.exception.UserNotFoundException;
import gift.domain.repository.UserRepository;
import gift.global.util.HashUtil;
import gift.global.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public UserResponse registerUser(UserRequest requestDto) {

        // 기존 회원인 경우 예외
        userRepository.findByEmail(requestDto.email()).ifPresent(p -> {
            throw new UserAlreadyExistsException();
        });

        return new UserResponse(jwtUtil.generateToken(userRepository.save(requestDto)));
    }

    public UserResponse loginUser(UserRequest requestDto) {
        // 존재하지 않은 이메일을 가진 유저로 로그인 시도
        // 존재한 경우 user 참조 반환
        User user = userRepository.findByEmail(requestDto.email())
            .orElseThrow(UserIncorrectLoginInfoException::new);

        // 유저는 존재하나 비밀번호가 맞지 않은 채 로그인 시도
        if (!HashUtil.hashCode(requestDto.password()).equals(user.password())) {
            throw new UserIncorrectLoginInfoException();
        }

        return new UserResponse(jwtUtil.generateToken(user));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
