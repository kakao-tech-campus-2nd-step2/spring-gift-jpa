package gift.user;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<SiteUser> findAllUser(){
        return userRepository.findAll();
    }

    public Optional<SiteUser> findById(Long id) {
        return userRepository.findById(id);
    }

    public SiteUser findUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email,password);
    }

    public void createUser(String username, String email, String password){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        this.userRepository.save(user);
    }
}
