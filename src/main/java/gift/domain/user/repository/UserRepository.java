package gift.domain.user.repository;

import gift.domain.user.User;
import gift.domain.user.dto.UserDTO;

public interface UserRepository {

    boolean existsByEmail(String email);

    void join(User user);

    boolean checkUserInfo(UserDTO userDTO);

    User findByEmailAndPassword(UserDTO userDTO);
}
