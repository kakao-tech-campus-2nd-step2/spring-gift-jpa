package gift.controller;

import gift.model.AccessTokenDO;
import gift.model.User;
import gift.repository.UserRepository;
import gift.util.UserUtility;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserUtility userUtility;

    @Autowired
    public UserController(UserRepository userRepository, UserUtility userUtility) {
        this.userRepository = userRepository;
        this.userUtility = userUtility;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody @Valid User user) {
        userRepository.save(user);
        String accessToken = userUtility.makeAccessToken(user);
        return ResponseEntity.ok().body(userUtility.accessTokenToObject(accessToken));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid User user) {
        Optional<User> result = userRepository.findByEmail(user.getEmail());
        if (!result.isPresent())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email does not exist");
        User foundUser = result.get();
        if (!user.getPassword().equals(foundUser.getPassword()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Password does not match");
        String accessToken = userUtility.makeAccessToken(user);
        return ResponseEntity.ok().body(userUtility.accessTokenToObject(accessToken));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AccessTokenDO accessToken) {
        Claims claims = userUtility.tokenParser(accessToken.getAccessToken());
        if (claims == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
        String email = claims.get("email", String.class);
        if (email == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
        Optional<User> result = userRepository.findByEmail(email);
        if (!result.isPresent())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email does not exist");
        return ResponseEntity.ok().body(userUtility.emailToObject(email));
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleForbidden(ResponseStatusException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
