package gift.service;

import gift.dto.user.*;
import gift.exception.InvalidPasswordException;
import gift.repository.UserDAO;
import gift.security.jwt.TokenProvider;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDAO userDAO;
    private final TokenProvider tokenProvider;


    public UserService(UserDAO userDAO, TokenProvider tokenProvider) {
        this.userDAO = userDAO;
        this.tokenProvider = tokenProvider;
    }

    public List<UserResponseDTO> getAllUsers() {

        return userDAO.findAll().stream().map((userInfo) -> new UserResponseDTO(
                userInfo.id(),
                userInfo.email()
        )).toList();
    }

    public static String hashPassword(String plainPw) {
        return BCrypt.hashpw(plainPw, BCrypt.gensalt());
    }

    public TokenResponseDTO signUp(UserRequestDTO userRequestDTO) {
        String email = userRequestDTO.email();
        String encryptedPW = hashPassword(userRequestDTO.password());

        UserInfoDTO userInfoDTO = userDAO.create(new UserEncryptedDTO(email, encryptedPW));

        String token = tokenProvider.generateToken(userInfoDTO.email());

        return new TokenResponseDTO(token);
    }

    public TokenResponseDTO login(UserRequestDTO userRequestDTO) throws InvalidPasswordException {
        UserInfoDTO userInfoDTO = userDAO.findUserByEmail(userRequestDTO.email());
        String encodedOriginalPw = userInfoDTO.encryptedPw();

        if (!BCrypt.checkpw(userRequestDTO.password(), encodedOriginalPw)) {
            throw new InvalidPasswordException("Invalid password");
        }

        String token = tokenProvider.generateToken(userInfoDTO.email());

        return new TokenResponseDTO(token);
    }

    public void deleteUser(long id) {
        userDAO.delete(id);
    }

    public void deleteUser(String email) {
        UserInfoDTO user = userDAO.findUserByEmail(email);

        userDAO.delete(user.id());
    }

    public void updatePw(long id, PwUpdateDTO pwUpdateDTO) {
        String encryptedPW = hashPassword(pwUpdateDTO.password());

        userDAO.updatePw(new EncryptedUpdateDTO(id, encryptedPW));
    }

    public void updatePw(String email, PwUpdateDTO pwUpdateDTO) {
        UserInfoDTO user = userDAO.findUserByEmail(email);

        String encryptedPW = hashPassword(pwUpdateDTO.password());

        userDAO.updatePw(new EncryptedUpdateDTO(user.id(), encryptedPW));
    }
}
