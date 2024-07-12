package gift.service;

import gift.authorization.JwtUtil;
import gift.dto.LoginUser;
import gift.dto.UserDTO;
import gift.entity.User;
import gift.exceptionhandler.DuplicateValueException;
import gift.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository repository , JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setEmail(dto.emali());
        user.setPassword(dto.password());
        return user;
    }

    public String signUp(UserDTO userDTO) {
        String email = userDTO.emali();
        repository.findByEmail(email)
                .ifPresent(value -> {
                    throw new DuplicateValueException("중복된 id입니다");
                });
        User user = toEntity(userDTO);
        repository.save(user);
        String token = jwtUtil.generateToken(user);
        return token;
    }

    public String login(UserDTO userDTO) {
        String email = userDTO.emali();
        Optional<User> existingUser = repository.findByEmail(email);
        existingUser.orElseThrow(() -> new DuplicateValueException("id와 비밀번호를 다시 확인하세요"));
        String token = jwtUtil.generateToken(existingUser.get());
        return token;
    }

    public void tokenLogin(LoginUser loginUser) {
        if(jwtUtil.ValidToken(loginUser)){
            throw new JwtException("토큰 인증이 불가능합니다");
        }
    }

    @Description("임시 확인용 service")
    public List<User> getAllUsers() {
        return repository.findAll();
    }

}
