package gift.service;

import gift.common.ErrorMessage;
import gift.dto.TokenDTO;
import gift.dto.UserDTO;
import gift.exceptions.InvalidUserException;
import gift.model.User;
import gift.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    @Value("${jwt.secretKey}")
    private String secretKey;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void redundantUser(String state, UserDTO userDTO) {
        boolean userExists = userRepository.countUsers(userDTO.email())
                .orElse(0) > 0;

        if (state.equals("login") & !userExists) {
            throw new InvalidUserException(ErrorMessage.emailNotExists);
        }

        if (state.equals("regist") & userExists) {
            throw new InvalidUserException(ErrorMessage.emailAlreadyExists);
        }
    }

    public void comparePassword(UserDTO userDTO) {
        User realUser = userRepository.selectUser(userDTO.email());
        if(!realUser.getPassword().equals(userDTO.password())) {
            throw new InvalidUserException(ErrorMessage.passwordInvalid);
        }
    }
}
