package gift.user.service;

import gift.user.dto.UserDto;
import gift.user.entity.User;
import gift.user.entity.UserRole;
import gift.user.repository.UserRepository;
import gift.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtTokenProvider tokenProvider;

  @Autowired
  public UserService(UserRepository userRepository, JwtTokenProvider tokenProvider) {
    this.userRepository = userRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
    this.tokenProvider = tokenProvider;
  }
  public String register(@Valid UserDto userDto) {
    if (userRepository.findByEmail(userDto.getEmail()).isPresent()){
      throw new RuntimeException("이미 존재하는 이메일입니다.");
    }

    User user = new User();
    user.setEmail(userDto.getEmail());
    user.setPassword(userDto.getPassword());
    user.setRole(userDto.getUserRole());
    return tokenProvider.createToken(user.getEmail());
  }

  public String authenticate(String email, String password) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다."));
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다.");
    }
    return tokenProvider.createToken(user.getEmail());
  }

  public User getMemberFromToken(String token) {
    String email = tokenProvider.getEmailFromToken(token);
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Invalid token"));
  }

}
