package gift.service;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gift.exception.InvalidUserException;
import gift.exception.UnauthorizedException;
import gift.exception.UserNotFoundException;
import gift.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

@Service
public class AuthService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcInsert jdbcInsert;
	
	private String secret = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
	private SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
	
	@PostConstruct
	public void setup() {
		this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("users")
				.usingGeneratedKeyColumns("id");
	}
	
	private static final class UserMapper implements RowMapper<User>{
		@Override
		public User mapRow(ResultSet resultSet, int rowNum) throws SQLException{
			User user = new User();
			user.setId(resultSet.getLong("id"));
			user.setEmail(resultSet.getString("email"));
			user.setPassword(resultSet.getString("password"));
			return user;
		}
	}
	
	public void createUser(User user, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new InvalidUserException(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", user.getEmail());
		parameters.put("password", user.getPassword());
		
		Number newId = jdbcInsert.executeAndReturnKey(parameters);
		user.setId(newId.longValue());
	}
	
	public String parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token.replace("Bearer ", ""))
                    .getPayload()
                    .get("email", String.class);
        } catch(Exception e) {
            throw new UnauthorizedException("Invalid or expired token");
        }
    }
	
	public User searchUser(String email, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new InvalidUserException(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
		String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{email}, new UserMapper());
        } catch (EmptyResultDataAccessException e) {
        	throw new UserNotFoundException("User with email " + email + " not found");
        }
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
		    .claim("email", user.getEmail())
		    .signWith(secretKey, SignatureAlgorithm.HS256)
		    .compact();
	}
}
