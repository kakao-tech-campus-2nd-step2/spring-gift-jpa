package gift.service;

import gift.DTO.LoginRequest;
import gift.DTO.LoginResponse;
import gift.DTO.SignupRequest;
import gift.DTO.SignupResponse;
import gift.DTO.User;
import gift.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Key key;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        String secretKey = "s3cr3tK3yF0rJWTt0k3nG3n3r@ti0n12345678"; // 256 bits
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public SignupResponse registerUser(SignupRequest signupRequest) {
        userRepository.findByEmail(signupRequest.getEmail()).ifPresent(p -> {
            throw new RuntimeException("Email already exists");
        });
        User user = new User(signupRequest.getEmail(), signupRequest.getPassword());
        userRepository.save(user);

        String welcome = "Welcome, " + user.getEmail() + "!";
        return new SignupResponse(welcome);
    }

    public LoginResponse loginUser(LoginRequest loginRequest) throws Exception {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail()); // Email이 PK
        user.orElseThrow(() -> new RuntimeException("Invalid email or password"));
        User registeredUser = user.get();
        if (registeredUser.getPassword().equals(loginRequest.getPassword())) {
            String token = generateToken(registeredUser);
            return new LoginResponse(token);
        }
        throw new Exception("Invalid email or password");
    }

    private String generateToken(User user) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
            .setSubject(user.getEmail())
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + 3600000)) // 1 hour validity
            .signWith(key)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
