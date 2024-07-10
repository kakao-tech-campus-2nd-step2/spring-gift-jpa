package gift.service;

import gift.dto.UserDto;
import gift.entity.User;
import gift.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Base64;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String save(UserDto.Request request) {

        User newUser = new User(request.getEmail(), request.getPassword());

        if (userRepository.save(newUser) != null) {
            return generateToken(request.getEmail(), request.getPassword());
        }
        return null;
    }

    public String generateToken(String userEmail, String userPassword) {

        String credentials = userEmail + ":" + userPassword;

        return "Basic" + Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    public UserDto login(UserDto inputInfo) throws AuthenticationException {

        UserDto dbUserDto = UserDto.fromEntity(userRepository.findByUserEmail(inputInfo.getEmail()));

        return validatePassword(inputInfo, dbUserDto);
    }

    private UserDto validatePassword(UserDto inputInfo, UserDto dbUserDto) throws AuthenticationException {

        if (inputInfo.getPassword().equals(dbUserDto.getPassword())) {
            return dbUserDto;
        }
        throw new AuthenticationException("Invalid password");
    }
}
