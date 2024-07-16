package gift.main.service;

import gift.main.Exception.CustomException;
import gift.main.Exception.ErrorCode;
import gift.main.dto.UserJoinRequest;
import gift.main.dto.UserLoginRequest;
import gift.main.entity.User;
import gift.main.repository.UserRepository;
import gift.main.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {


    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public UserService(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Transactional
    public String joinUser(UserJoinRequest userJoinRequest) {
        //유효성 검사해야하는데용~!
        if (userRepository.existsByEmail(userJoinRequest.email())) {
            throw new CustomException(ErrorCode.ALREADY_EMAIL);
        }
        User userdto = new User(userJoinRequest);
        User user = userRepository.save(userdto);
        return jwtUtil.createToken(user);

    }


    public String loginUser(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByEmailAndPassword(userLoginRequest.email(), userLoginRequest.password())
                .orElseThrow(() -> new CustomException(ErrorCode.ERROR_LOGIN));
        return jwtUtil.createToken(user);
    }


}
