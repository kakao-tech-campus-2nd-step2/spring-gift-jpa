package gift.service;

import gift.DTO.LoginRequest;
import gift.DTO.SignupRequest;
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

    public String registerUser(SignupRequest signupRequest) {
        User user = new User(signupRequest.getEmail(), signupRequest.getPassword());
        userRepository.addUser(user);
        return "Welcome, " + user.getEmail() + "!";
    }

    public String loginUser(LoginRequest loginRequest) throws Exception {
        Optional<User> user = userRepository.findUserByEmail(loginRequest.getEmail()); // Email이 PK
        if (user.isPresent() &&
            user.get().getPassword().equals(loginRequest.getPassword())) {
            return generateToken(user.get());
        } else {
            throw new Exception("Invalid email or password");
        }
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
