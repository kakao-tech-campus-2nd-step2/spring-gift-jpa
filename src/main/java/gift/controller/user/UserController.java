package gift.controller.user;

import gift.annotation.TokenEmail;
import gift.dto.user.PwUpdateDTO;
import gift.dto.user.UserResponseDTO;
import gift.exception.ForbiddenRequestException;
import gift.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }



    @DeleteMapping("/api/users")
    public ResponseEntity<Void> deleteUser(@TokenEmail String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/password-change")
    public ResponseEntity<String> updatePw(@TokenEmail String email, @RequestBody @Valid PwUpdateDTO pwUpdateDTO) {
        final boolean FORBIDDEN = true;

        if (FORBIDDEN) {
            throw new ForbiddenRequestException("password changing is not allowed");
        }

        userService.updatePw(email, pwUpdateDTO);

        return ResponseEntity.ok("Password updated successfully");
    }
}
