package gift.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public boolean registerUser(User user) {
        if (userDao.userExistsByEmail(user.getEmail())) {
            return false;
        }
        userDao.insertUser(user.getEmail(),user.getPassword());
        return true;
    }

    public Boolean getUserByEmailAndPassword(User user){
        return userDao.userExistsByEmailAndPassword(user.getEmail(),user.getPassword());
    }

    public Boolean getUserByEmail(User user){
        return userDao.userExistsByEmail(user.getEmail());
    }
}
