package gift.Service;

import gift.Entity.Users;
import gift.Model.User;
import gift.Repository.UsersJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Qualifier("userService")
public class UserService {

    private final UsersJpaRepository usersJpaRepository;

    @Autowired
    public UserService(UsersJpaRepository usersJpaRepository) {
        this.usersJpaRepository = usersJpaRepository;
    }

    public void register(User user) {
        Users users = Users.createUsers(user);
        usersJpaRepository.save(users);
    }

    public Optional<Users> findByEmail(String email) {
        return usersJpaRepository.findByEmail(email);
    }

    public boolean isAdmin(String email) {
        Optional<Users> user = usersJpaRepository.findByEmail(email);
        return user.get().isAdmin();
    }

    public boolean authenticate(String email, String password) {
        Optional<Users> user = usersJpaRepository.findByEmailAndPassword(email, password);
        return user != null && user.get().getPassword().equals(password);
    }


}
