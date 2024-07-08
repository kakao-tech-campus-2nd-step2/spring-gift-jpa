package gift.service;

import gift.model.User;
import gift.repository.UserRepository;
import gift.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(Long id, User user) {
        userRepository.update(id, user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Long authenticateUser(String email, String password) {
        return userRepository.getIdByEmailPassword(email, password);
    }

    public String generateToken(String email) {
        return JwtUtil.generateToken(email);
    }

    public boolean isValidToken(String token) {
        return JwtUtil.isValidToken(token);
    }
}
