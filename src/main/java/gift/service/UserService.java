package gift.service;

import gift.entity.User;
import gift.entity.UserDTO;
import gift.repository.UserRepository;
import gift.util.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserUtility userUtility;

    @Autowired
    public UserService(UserRepository userRepository, UserUtility userUtility) {
        this.userRepository = userRepository;
        this.userUtility = userUtility;
    }

    public String signup(UserDTO user) {
        User savedUser = userRepository.save(new User(user));
        return userUtility.makeAccessToken(savedUser);
    }

    public String login(UserDTO user) {
        Optional<User> result = userRepository.findByEmail(user.getEmail());
        if (!result.isPresent())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email does not exist");
        User foundUser = result.get();
        if (!user.getPassword().equals(foundUser.getPassword()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Password does not match");
        return userUtility.makeAccessToken(foundUser);
    }
}
