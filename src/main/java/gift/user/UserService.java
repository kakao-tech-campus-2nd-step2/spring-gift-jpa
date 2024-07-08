package gift.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public boolean registerUser(Users users) {
        if (usersRepository.existsByEmail(users.getEmail())) {
            return false;
        }
        usersRepository.save(users);
        return true;
    }

    public Boolean getUserByEmailAndPassword(Users users){
        return usersRepository.existsByEmailAndPassword(users.getEmail(), users.getPassword());
    }
}
