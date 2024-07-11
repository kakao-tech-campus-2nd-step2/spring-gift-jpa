package gift.service;

import gift.dto.user.*;
import gift.entity.User;
import gift.exception.InvalidPasswordException;
import gift.repository.UserRepository;
import gift.security.jwt.TokenProvider;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;


    public UserService(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll().stream().map((user) -> new UserResponseDTO(
                user.getId(),
                user.getEmail()
        )).toList();
    }

    public static String hashPassword(String plainPw) {
        return BCrypt.hashpw(plainPw, BCrypt.gensalt());
    }

    public TokenResponseDTO signUp(UserRequestDTO userRequestDTO) {
        String email = userRequestDTO.email();
        String encryptedPW = hashPassword(userRequestDTO.password());

        User user = userRepository.save(new User(email, encryptedPW));

        String token = tokenProvider.generateToken(user.getEmail());

        return new TokenResponseDTO(token);
    }

    public TokenResponseDTO login(UserRequestDTO userRequestDTO) throws InvalidPasswordException {
        User user = userRepository.findByEmail(userRequestDTO.email());
        String encodedOriginalPw = user.getEncryptedPw();

        if (!BCrypt.checkpw(userRequestDTO.password(), encodedOriginalPw)) {
            throw new InvalidPasswordException("Invalid password");
        }

        String token = tokenProvider.generateToken(user.getEmail());

        return new TokenResponseDTO(token);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email);

        userRepository.deleteById(user.getId());
    }

    public void updatePw(long id, PwUpdateDTO pwUpdateDTO) {
        String encryptedPW = hashPassword(pwUpdateDTO.password());

        User user = userRepository.findById(id).get();
        user.setEncryptedPw(encryptedPW);

        userRepository.save(user);
    }

    public void updatePw(String email, PwUpdateDTO pwUpdateDTO) {
        String encryptedPW = hashPassword(pwUpdateDTO.password());

        User user = userRepository.findByEmail(email);
        user.setEncryptedPw(encryptedPW);

        userRepository.save(user);
    }
}
