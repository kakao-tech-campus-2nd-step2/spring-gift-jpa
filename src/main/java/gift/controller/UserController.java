package gift.controller;

import gift.model.User;
import gift.service.UserService;
import gift.dto.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody User user) {
        try {
            userService.createUser(user);
            TokenResponse tokenResponse = userService.createTokenResponse(user);
            return new ResponseEntity<>(tokenResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody User user) {
        try {
            User savedUser = userService.loadOneUser(user.getEmail());
            if (savedUser != null && savedUser.getPassword().equals(user.getPassword())) {
                TokenResponse tokenResponse = userService.createTokenResponse(savedUser);
                return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}