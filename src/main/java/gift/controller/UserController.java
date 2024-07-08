package gift.controller;

import gift.dto.TokenResponseDto;
import gift.dto.UserRequestDto;
import gift.service.JwtUtil;
import gift.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> registerUser(@RequestBody UserRequestDto request){
        userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenResponseDto());
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> giveAccessToken(@RequestBody UserRequestDto request) {
        userService.authenticate(request.getEmail(),request.getPassword());
        String token = jwtUtil.generateToken(request.getEmail());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(new TokenResponseDto(token));
    }
}
