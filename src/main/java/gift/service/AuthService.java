package gift.service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gift.exception.InvalidUserException;
import gift.exception.UnauthorizedException;
import gift.exception.UserNotFoundException;
import gift.model.User;
import gift.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRespository;
	
	private final String secret = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
	
	public void createUser(User user, BindingResult bindingResult) {
		validateBindingResult(bindingResult);
		userRespository.save(user);
	}
	
	public User searchUser(String email, BindingResult bindingResult) {
		validateBindingResult(bindingResult);       
        return findByEmail(email);
	}
	
	public Map<String, String> loginUser(User user, BindingResult bindingResult){
		User registeredUser = searchUser(user.getEmail(), bindingResult);
		validatePassword(user.getPassword(), registeredUser.getPassword());
		String token = grantAccessToken(registeredUser);
		return loginResponse(token);
	}
	
	public String grantAccessToken(User user) {
		return Jwts.builder()
		    .setSubject(user.getId().toString())
		    .claim("userEmail", user.getEmail())
		    .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
		    .compact();
	}
	
	public String parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token.replace("Bearer ", ""))
                    .getPayload()
                    .get("userEmail", String.class);
        } catch(Exception e) {
            throw new UnauthorizedException("Invalid or expired token");
        }
    }
	
	private void validateBindingResult(BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			String errorMessage = bindingResult
					.getFieldError()
					.getDefaultMessage();
			throw new InvalidUserException(errorMessage, HttpStatus.BAD_REQUEST);
		}
	}
	
	private void validatePassword(String inputPassword, String storedPassword) {
		if(!inputPassword.equals(storedPassword)) {
			throw new InvalidUserException("The email doesn't or thr password is incorrect.", HttpStatus.FORBIDDEN);
		}
	}
	
	private Map<String, String> loginResponse(String token){
		Map<String, String> response = new HashMap<>();
		response.put("token", token);
		return response;
	}
	
	private User findByEmail(String email) {
		return userRespository.findByEmail(email)
	    		.orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
	}
}