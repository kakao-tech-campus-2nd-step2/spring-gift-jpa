package gift.service;

import gift.exception.CustomException.UserNotFoundException;
import gift.exception.ErrorCode;
import gift.model.user.User;
import gift.model.user.UserDTO;
import gift.model.user.UserForm;
import gift.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long insertUser(UserForm userForm) {
        userRepository.save(new User(0L, userForm.getEmail(), userForm.getPassWord()));
        return userRepository.findByEmail(userForm.getEmail())
            .orElseThrow(() -> new UserNotFoundException(
                ErrorCode.USER_NOT_FOUND)).getId();
    }

    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        return new UserDTO(user.getId(), user.getEmail(), user.getPassWord());
    }

    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isPassWordMatch(UserForm userForm) {
        return userForm.getPassWord()
            .equals(userRepository.findByEmail(userForm.getEmail())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND))
                .getPassWord());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
