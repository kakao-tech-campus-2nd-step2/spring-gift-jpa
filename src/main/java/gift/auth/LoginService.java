package gift.auth;

import gift.entity.UserEntity;
import gift.errorException.BaseHandler;
import gift.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtToken jwtToken;

    public Token Login(Login login) {
        UserEntity user = userRepository.findAllByEmailAndPasswordAndIsDelete(login.getEmail(),
            login.getPassword(), 0).orElseThrow(
            () -> new BaseHandler(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다.")
        );
        return jwtToken.createToken(user);
    }
}
