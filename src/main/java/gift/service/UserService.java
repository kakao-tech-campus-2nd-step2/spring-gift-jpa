package gift.service;

import gift.dto.UserDTO;
import gift.model.User;
import gift.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(UserDTO userDTO) {
        User user = new User(null, userDTO.name(),userDTO.email(),userDTO.password(),"user");
        return userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}