package gift.service;

import gift.PasswordEncoder;
import gift.converter.UserConverter;
import gift.dto.UserDTO;
import gift.model.User;
import gift.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
@Service
public class UserService {

    private final UserRepository userRepository;
    private final String secretKey;


    public UserService(UserRepository userRepository, @Value("${jwt.secret.key}") String secretKey) {
        this.userRepository = userRepository;
        this.secretKey = secretKey;
    }

    public UserDTO register(UserDTO userDTO) {
        User user = UserConverter.convertToEntity(userDTO);
        user = new User(null, user.getEmail(), PasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return UserConverter.convertToDTO(user);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.matchesPassword(password)) {
            return Jwts.builder()
                .setSubject(email) // 여기에서 사용자 이메일을 주체로 설정
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
        }
        return null;
    }
}