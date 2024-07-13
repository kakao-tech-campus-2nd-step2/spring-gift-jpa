package gift.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import gift.controller.AuthController;
import gift.model.User;
import gift.service.AuthService;

public class AuthTest {

	@Mock
	private AuthService authService;
	
	@InjectMocks
	private AuthController authController;
	
	@Mock BindingResult bindingResult;
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
    public void testRegister() {
		User user = new User("test@test.com", "pw");
        doNothing().when(authService).createUser(any(User.class), any(BindingResult.class));
        ResponseEntity<String> response = authController.register(user, bindingResult);
        
        assertEquals("User registered successfully", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
	
	@Test
    public void testLogin() {
		User user = new User("test@test.com", "pw");
		
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", "dummyToken");
        when(authService.loginUser(any(User.class), any(BindingResult.class))).thenReturn(tokenMap);
        ResponseEntity<Map<String, String>> response = authController.login(user, bindingResult);

        assertEquals("dummyToken", response.getBody().get("token"));
        assertEquals(200, response.getStatusCodeValue());
    }
}
