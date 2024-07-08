package gift.controller;


import gift.dto.UserDTO;
import gift.service.AuthService;
import gift.jwtutil.JwtUtil;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@Controller
@RequestMapping("/members/login")
public class LoginController {

    @Autowired
    private AuthService authService;
    private final JwtUtil jwtUtil;

    public LoginController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public String loginPage() {
        return "login";
    }

    @PostMapping
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserDTO userDTO) {
        authService.redundantUser("login", userDTO);
        authService.comparePassword(userDTO);

        return new ResponseEntity<>(jwtUtil.makeToken(userDTO), HttpStatus.OK);
    }

}
