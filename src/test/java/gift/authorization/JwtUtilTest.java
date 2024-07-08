package gift.authorization;

import gift.entity.LoginUser;
import gift.entity.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@SpringBootTest
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    private User testUser;
    private String validToken;
    private String invalidToken;

    @BeforeEach
    public void setUp() {
        // Initialize test user
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setType("user");

        // Generate a valid token for testing
        validToken = jwtUtil.generateToken(testUser);

        // Prepare an invalid token (manually created for testing)
        invalidToken = "invalid.token.abcdef123456";
    }

    @Test
    public void testGenerateToken() {
        String token = jwtUtil.generateToken(testUser);
        Assertions.assertNotNull(token);
    }

    @Test
    public void testExtractClaims() {
        Claims claims = jwtUtil.extractClaims(validToken);
        Assertions.assertNotNull(claims);
        Assertions.assertEquals("test@example.com", claims.get("email", String.class));
        Assertions.assertEquals("user", claims.get("type", String.class));
    }

    @Test
    public void testGetUserType() {
        String userType = jwtUtil.getUserType(validToken);
        Assertions.assertEquals("user", userType);
    }

    @Test
    public void testGetUserEmail() {
        String userEmail = jwtUtil.getUserEmail(validToken);
        Assertions.assertEquals("test@example.com", userEmail);
    }

    @Test
    public void testCheckClaim_ValidToken() {
        boolean isValid = jwtUtil.checkClaim(validToken);
        Assertions.assertTrue(isValid);
    }

    @Test
    public void testCheckClaim_InvalidToken() {
        boolean isValid = jwtUtil.checkClaim(invalidToken);
        Assertions.assertFalse(isValid);
    }

    @Test
    public void testValidToken_Valid() {
        LoginUser loginUser = new LoginUser();
        loginUser.setToken(validToken);

        ResponseEntity<String> response = jwtUtil.ValidToken(loginUser);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testValidToken_Invalid() {
        LoginUser loginUser = new LoginUser();
        loginUser.setToken(invalidToken);

        ResponseEntity<String> response = jwtUtil.ValidToken(loginUser);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}