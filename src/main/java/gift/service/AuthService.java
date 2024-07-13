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
	
	private String secret = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
	
	public void createUser(User user, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new InvalidUserException(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
		userRespository.save(user);
	}
	
	public User searchUser(String email, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new InvalidUserException(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}        
        return userRespository.findByEmail(email)
        		.orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
	}
	
	public Map<String, String> loginUser(User user, BindingResult bindingResult){
		User registeredUser = searchUser(user.getEmail(), bindingResult);
		if(!registeredUser.getPassword().equals(user.getPassword())) {
			throw new InvalidUserException("The email doesn't exist or the password id incorrect.", HttpStatus.FORBIDDEN);
		}
		String token = grantAccessToken(registeredUser);
		Map<String, String> response = new HashMap<>();
		response.put("token", token);
		return response;
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
}
