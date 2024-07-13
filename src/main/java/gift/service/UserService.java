package gift.service;

import gift.dto.TokenDto;
import gift.dto.UserDto;
import gift.entity.User;
import gift.repository.UserRepositoryInterface;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
public class UserService {
    private final UserRepositoryInterface userRepositoryInterface;
    private final TokenService tokenService;

    public UserService(UserRepositoryInterface userRepositoryInterface, TokenService tokenService) {
        this.userRepositoryInterface = userRepositoryInterface;
        this.tokenService = tokenService;
    }

    public TokenDto save(String email, String password) {

        User newUser = new User(email, password);

        User actualUser = userRepositoryInterface.save(newUser);
        return generateTokenDtoFrom(actualUser.getEmail());
    }


    public List<UserDto.Response> getAll() {
        return userRepositoryInterface.findAll().stream().map(UserDto.Response::fromEntity).toList();
    }

    public TokenDto generateTokenDtoFrom(String userEmail) {
        Long userId = findUserIdFrom(userEmail);
        return TokenDto.fromEntity(tokenService.makeTokenFrom(userId));
    }

    private Long findUserIdFrom(String userEmail) {
        return userRepositoryInterface.findByEmail(userEmail).getId();
    }

    public boolean login(String email, String password) throws AuthenticationException {
        UserDto.Response dbUserDto = UserDto.Response.fromEntity(userRepositoryInterface.findByEmail(email));

        return validatePassword(password, dbUserDto.getPassword());
    }

    private boolean validatePassword(String inputPassword, String dbUserPassword) throws AuthenticationException {

        if (inputPassword.equals(dbUserPassword)) {
            return true;
        }
        throw new AuthenticationException("Invalid password");
    }
}
