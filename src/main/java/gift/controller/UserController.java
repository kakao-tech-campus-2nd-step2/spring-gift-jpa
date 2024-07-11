package gift.controller;

import gift.model.AccessTokenDO;
import gift.model.User;
import gift.model.UserDTO;
import gift.repository.UserRepository;
import gift.service.UserService;
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
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserUtility userUtility, UserService userService) {
        this.userRepository = userRepository;
        this.userUtility = userUtility;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody @Valid UserDTO user) {
        User savedUser = userRepository.save(user);
        String accessToken = userUtility.makeAccessToken(savedUser);
        return ResponseEntity.ok().body(userUtility.accessTokenToObject(accessToken));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserDTO user) {
        String accessToken = userService.login(user);
        return ResponseEntity.ok().body(userUtility.accessTokenToObject(accessToken));
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleForbidden(ResponseStatusException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
