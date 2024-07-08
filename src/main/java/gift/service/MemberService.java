package gift.service;

import gift.exception.user.UserNotFoundException;
import gift.model.User;
import gift.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("해당 email의 계정이 존재하지 않습니다."));
        return user;
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("해당 email의 계정이 존재하지 않습니다."));
        return user;
    }
}
