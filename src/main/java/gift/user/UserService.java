package gift.user;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(UserDTO userDTO) {

        return userRepository.insertUser(
                new UserDTO(
                        userDTO.getEmail(),
                        userDTO.getPassword(),
                        userDTO.getNickname()));
    }

    public boolean validateUser(LoginDTO loginDTO) {
        User user = getUserByEmail(loginDTO.email);
        return Objects.equals(loginDTO.getPassword(), user.getPassword());
    }

    public boolean checkIfDuplicatedEmail(String email){
        try {
            Optional<User> user = userRepository.findByEmail(email);
        }
        catch (EmptyResultDataAccessException e){
            return false;
        }
        return true;

    }

    public UserDTO getUserDTOByLoginDTO(LoginDTO loginDTO){
        User user = userRepository.findByEmail(loginDTO.email)
                .orElseThrow();
        return new UserDTO(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    public User getUserById(Long id){
        return userRepository.selectUser(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

}
