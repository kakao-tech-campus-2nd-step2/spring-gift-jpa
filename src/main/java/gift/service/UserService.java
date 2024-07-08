package gift.service;

import gift.dto.UserResponseDto;
import gift.entity.User;
import gift.entity.UserDao;
import gift.exception.BusinessException;
import gift.exception.ErrorCode;
import gift.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserDao userDao;
    private final TokenService tokenService;

    public UserService(UserDao userDao, TokenService tokenService) {
        this.userDao = userDao;
        this.tokenService = tokenService;
    }

    public UserResponseDto registerUser(String email, String password) {
        userDao.selectUserByEmail(email)
                .ifPresent(user -> {
                    throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
                });

        User user = new User(email, password);
        User createdUser = userDao.insertUser(user);
        return UserMapper.toUserResponseDTO(createdUser);
    }

    public String loginUser(String email, String password) {
        User user = userDao.selectUserByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

        if (!user.isPasswordCorrect(password)) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }

        return tokenService.generateToken(user.email);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userDao.selectAllUsers();
        return users.stream()
                .map(UserMapper::toUserResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id) {
        User user = userDao.selectUserById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return UserMapper.toUserResponseDTO(user);
    }

    public UserResponseDto updateUser(Long id, String email, String password) {
        User existingUser = userDao.selectUserById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        existingUser.update(email, password);
        userDao.updateUser(existingUser);
        return UserMapper.toUserResponseDTO(existingUser);
    }

    public void deleteUser(Long id) {
        userDao.selectUserById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        userDao.deleteUser(id);
    }

    public Optional<User> findUserByEmail(String email) {
        return userDao.selectUserByEmail(email);
    }
}
