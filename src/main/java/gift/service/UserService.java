package gift.service;

import gift.domain.User;
import gift.repository.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    public Long register(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            return -1L;
        }
        userRepository.addUser(user);
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        return userOptional.get().getId();
    }

    public Optional<User> login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()&&user.get().getPassword().equals(password)){
            return user;
        }
        return Optional.empty();
    }

    public Long getUserId(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return user.get().getId();
        }
        return -1L;
    }

}