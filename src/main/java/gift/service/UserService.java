package gift.service;

import gift.exception.CustomException.UserNotFoundException;
import gift.exception.ErrorCode;
import gift.model.user.User;
import gift.model.user.UserDTO;
import gift.model.user.UserForm;
import gift.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long insertUser(UserForm userForm) {
        userRepository.save(new User(0L, userForm.getEmail(), userForm.getPassword()));
        return userRepository.findByEmail(userForm.getEmail())
            .orElseThrow(() -> new UserNotFoundException(
                ErrorCode.USER_NOT_FOUND)).getId();
    }

    @Transactional(readOnly = true)
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        return new UserDTO(user.getId(), user.getEmail(), user.getPassword());
    }

    @Transactional(readOnly = true)
    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean isPasswordMatch(UserForm userForm) {
        return userForm.getPassword()
            .equals(userRepository.findByEmail(userForm.getEmail())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND))
                .getPassword());
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
