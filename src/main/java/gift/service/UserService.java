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

    public TokenDto save(UserDto.Request request) {

        User newUser = new User(request.getEmail(), request.getPassword());

        User actualUser = userRepositoryInterface.save(newUser);
        return generateTokenDtoFrom(actualUser.getEmail());
    }


    public List<UserDto> getAll() {
        return userRepositoryInterface.findAll().stream().map(UserDto::fromEntity).toList();
    }

    public TokenDto generateTokenDtoFrom(String userEmail) {
        Long userId = findUserIdFrom(userEmail);
        return TokenDto.fromEntity(tokenService.makeTokenFrom(userId));
    }

    private Long findUserIdFrom(String userEmail) {
        return userRepositoryInterface.findByEmail(userEmail).getId();
    }

    public UserDto login(UserDto inputInfo) throws AuthenticationException {
        UserDto dbUserDto = UserDto.fromEntity(userRepositoryInterface.findByEmail(inputInfo.getEmail()));
        return validatePassword(inputInfo, dbUserDto);
    }

    private UserDto validatePassword(UserDto inputInfo, UserDto dbUserDto) throws AuthenticationException {

        if (inputInfo.getPassword().equals(dbUserDto.getPassword())) {
            return dbUserDto;
        }
        throw new AuthenticationException("Invalid password");
    }
}
