package gift.service;


import gift.dto.LoginResponseDTO;
import gift.dto.UserRequestDTO;
import gift.dto.UserResponseDTO;
import gift.exception.DuplicateException;
import gift.model.User;
import gift.repository.UserRepository;
import gift.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void register(UserRequestDTO userRequestDTO) {
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            throw new DuplicateException("이미 존재하는 회원입니다.");
        }
        User user = new User(userRequestDTO.getEmail(), userRequestDTO.getPassword());
        userRepository.save(user);
    }


    public LoginResponseDTO authenticate(UserRequestDTO userRequestDTO) {
        Optional<User> userOpt = userRepository.findByEmail(userRequestDTO.getEmail());
        if (userOpt.isEmpty() || !userOpt.get().checkPassword(userRequestDTO.getPassword())) {
            throw new IllegalArgumentException("유효하지 않은 이메일 or 비밀번호입니다.");
        }
        String token = JwtUtil.generateToken(userRequestDTO.getEmail());
        return new LoginResponseDTO(token);
    }

    public UserResponseDTO findByToken(UserRequestDTO userRequest) {
        String email = JwtUtil.extractEmail(userRequest.getToken());
        if (!JwtUtil.validateToken(userRequest.getToken(), email)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return new UserResponseDTO(user.getId(), user.getEmail());
    }
}
