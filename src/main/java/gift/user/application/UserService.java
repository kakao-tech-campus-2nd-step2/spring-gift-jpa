package gift.user.application;


import gift.user.domain.User;
import gift.user.domain.UserRegisterRequest;
import gift.user.infra.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserRegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        System.out.println("user.getPassword() = " + user.getPassword());
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }


}


