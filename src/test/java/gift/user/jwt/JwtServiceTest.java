package gift.user.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import gift.user.model.dto.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    public void testCreateToken() {
        Long id = 1L;
        String token = jwtService.createToken(id);
        assertNotNull(token);
        System.out.println("Generated Token: " + token);
    }

    @Test
    public void testGetEmailFromToken() {
        Long id = 1L;
        User user = new User(id, "yoo@example.com", "1234", "ADMIN");
        String token = jwtService.createToken(id);

        User loginUser = jwtService.getLoginUser(token);
        assertEquals(user, loginUser);
    }
}
