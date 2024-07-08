package gift.service;

import gift.authorization.JwtUtil;
import gift.entity.LoginUser;
import gift.entity.User;
import gift.repository.JdbcUserRepository;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final JdbcUserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(JdbcUserRepository repository , JwtUtil jwtUtil) {
        this.userRepository = repository;
        this.jwtUtil = jwtUtil;
    }
    
    // 에러 터지면 -> global에서 처리해줌
    public ResponseEntity<Map<String, String>> signUp(User user) {
        userRepository.save(user);
        String token = jwtUtil.generateToken(user);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("token", token);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body(responseBody);
    }

    @Description("임시 확인용 service")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public ResponseEntity<String> login(User user) {
        if(userRepository.isExistUser(user)
                .isEmpty()){
            return new ResponseEntity<>("id와 비밀번호를 다시 확인하세요", HttpStatus.FORBIDDEN);
        }
        String token = jwtUtil.generateToken(user);
        /*System.out.println(token);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("token", token);*/
        return new ResponseEntity<>(token , HttpStatus.OK);
    }

    public ResponseEntity<String> tokenLogin(LoginUser loginUser) {
        return jwtUtil.ValidToken(loginUser);
    }

}
