package gift.user;

import gift.util.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

    public UserDTO findUserById(long id) throws NotFoundException {
        return UserDTO.fromUser(userRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    public void register(UserDTO user){
        String password = user.password();
        String email = user.email();
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일과 비밀번호는 비어있으면 안됩니다.");
        }
        if (!registerUser(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하는 이메일입니다.");
        }
    }

    public String login(UserDTO user){
        String email = user.email();
        String password = user.password();
        Map<String, String> map = new HashMap<>();
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일과 비밀번호는 빈칸이면 안됩니다.");
        }
        if(!getUserByEmailAndPassword(user)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        String token = jwtUtil.generateToken(user);
        if (token != null) {
            return "access-token: " + token;
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

    public UserDTO findById(long id) throws NotFoundException {
        return UserDTO.fromUser(userRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    public UserDTO findUserByEmail(String email){
        return UserDTO.fromUser(userRepository.findByEmail(email));
    }

    public Boolean getUserByEmailAndPassword(UserDTO userDTO) {
        User user = userDTO.toUser();
        return userRepository.existsByEmailAndPassword(user.getEmail(), user.getPassword());
    }
}
