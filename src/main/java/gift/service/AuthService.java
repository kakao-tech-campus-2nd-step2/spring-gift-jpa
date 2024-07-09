package gift.service;

import gift.dto.UserDto;
import gift.entity.User;
import gift.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String save(UserDto.Request request) {

        User newUser= new User(request.getEmail(), request.getPassword());

        if (userRepository.save(newUser) != null) {
            return generateToken(request.getEmail(), request.getPassword());
        }
        return "";
    }

    public String generateToken(String userEmail, String userPassword) {
        String credentials = userEmail + ":" + userPassword;
        return "Basic" + Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    public UserDto login(UserDto.Request inputInfo) {
        UserDto dbUserDto = UserDto.fromEntity(userRepository.findByUserEmail(inputInfo.getEmail()));
        return checkPassword(inputInfo, dbUserDto);
    }

    private UserDto checkPassword(UserDto.Request inputInfo, UserDto dbUserDto) {
        if (inputInfo.getPassword().equals(dbUserDto.getPassword())) {
            return dbUserDto;
        }
        return null;
    }
}
