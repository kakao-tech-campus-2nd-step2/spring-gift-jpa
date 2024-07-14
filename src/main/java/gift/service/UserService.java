package gift.service;

import gift.dto.user.UserLoginRequest;
import gift.dto.user.UserRegisterRequest;
import gift.dto.user.UserResponse;
import gift.entity.User;
import gift.exception.InvalidTokenException;
import gift.exception.user.UserAlreadyExistException;
import gift.exception.user.UserNotFoundException;
import gift.repository.UserRepository;
import gift.util.auth.JwtUtil;
import gift.util.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public UserService(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse registerUser(UserRegisterRequest request) {
        userRepository.findByEmail(request.email())
            .ifPresent(user -> {
                throw new UserAlreadyExistException("이미 존재하는 Email입니다.");
            });
        User registeredUser = userRepository.save(UserMapper.toUser(request));

        return UserMapper.toResponse(jwtUtil.generateToken(registeredUser.getId(),
            registeredUser.getEmail()));
    }

    @Transactional(readOnly = true)
    public UserResponse loginUser(UserLoginRequest userRequest) {
        User user = userRepository.findByEmailAndPassword(userRequest.email(),
                userRequest.password())
            .orElseThrow(() -> new UserNotFoundException("로그인할 수 없습니다."));

        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        return UserMapper.toResponse(token);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public Long getUserIdByToken(String token) {
        Long userId = jwtUtil.extractUserId(token);
        if (userId == null || !userRepository.existsById(userId)) {
            throw new InvalidTokenException();
        }
        return userId;
    }

}
