package gift.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gift.model.User;
import gift.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/members")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody User user, BindingResult bindingResult){
		authService.createUser(user, bindingResult);
        return ResponseEntity.ok("User registered successfully");
	}
	
	@PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody User user, BindingResult bindingResult){
		Map<String, String> loginResponse = authService.loginUser(user, bindingResult);
        return ResponseEntity.ok(loginResponse);
    }
}
