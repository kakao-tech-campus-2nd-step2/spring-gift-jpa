package gift.service;

import gift.domain.model.entity.User;
import gift.domain.model.dto.UserRequestDto;
import gift.domain.model.dto.UserResponseDto;
import gift.domain.repository.UserRepository;
import gift.exception.BadCredentialsException;
import gift.exception.DuplicateEmailException;
import gift.exception.NoSuchEmailException;
import gift.util.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public UserResponseDto joinUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new DuplicateEmailException("이미 가입한 이메일입니다.");
        }

        String hashedPassword = BCrypt.hashpw(userRequestDto.getPassword(), BCrypt.gensalt());

        User user = new User(userRequestDto.getEmail(), hashedPassword);
        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(userRequestDto.getEmail());

        return new UserResponseDto(savedUser, token);
    }

    public String loginUser(UserRequestDto userRequestDto) {
        User user = userRepository.findByEmail(userRequestDto.getEmail())
            .orElseThrow(() -> new NoSuchEmailException("사용자를 찾을 수 없습니다."));

        if (!BCrypt.checkpw(userRequestDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    public User getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new NoSuchEmailException("사용자를 찾을 수 없습니다."));
    }
}
