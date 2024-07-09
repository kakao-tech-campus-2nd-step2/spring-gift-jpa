package gift.Service;

import gift.Exception.ForbiddenException;
import gift.Model.User;
import gift.Repository.UserRepository;
import gift.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    public UserService (UserRepository userRepository, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public void signUpUser(User user){
        userRepository.insertUser(user.getEmail(), user.getPassword());
    }


    public String loginUser(User user) throws ForbiddenException {
        String temp = userRepository.login(user.getEmail(), user.getPassword());
        if (!(temp.equals(user.getPassword())))
            throw new ForbiddenException("잘못된 로그인입니다");

        return jwtUtil.generateToken(user);
    }

    public User getUserByToken(String token) {
        User user = userRepository.findByEmail(jwtUtil.getSubject(token));
        return user;
    }
}
