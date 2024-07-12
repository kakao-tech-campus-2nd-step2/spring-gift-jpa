package gift.Service;

import gift.Entity.UserEntity;
import gift.Entity.WishEntity;
import gift.Repository.UserRepository;
import gift.Repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WishRepository wishRepository;

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        // 관련된 WishEntity들도 삭제
        List<WishEntity> wishes = wishRepository.findByUserId(id);
        wishRepository.deleteAll(wishes);

        userRepository.deleteById(id);
    }
}
