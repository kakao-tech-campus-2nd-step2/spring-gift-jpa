package gift.user;

import gift.auth.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public boolean register(UserDTO user){
        String password = user.getPassword();
        String email = user.getEmail();
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일과 비밀번호는 비어있으면 안됩니다.");
        }
        if (!registerUser(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하는 이메일입니다.");
        }
        return true;
    }

    public String login(UserDTO user){
        String email = user.getEmail();
        String password = user.getPassword();
        Map<String, String> map = new HashMap<>();
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일과 비밀번호는 빈칸이면 안됩니다.");
        }
        if(!getUserByEmailAndPassword(user)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        String token = jwtUtil.generateToken(user);
        if (token != null) {
            String response = "access-token: " + token;
            return response;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰 생성 실패");
    }

    public boolean registerUser(UserDTO userDTO) {
        User user = userDTO.toUser();
        if (userRepository.existsByEmail(user.getEmail())) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public Boolean getUserByEmailAndPassword(UserDTO userDTO) {
        User user = userDTO.toUser();
        return userRepository.existsByEmailAndPassword(user.getEmail(), user.getPassword());
    }
}
