package gift.repository;

import gift.common.exception.ProductNotFoundException;
import gift.common.exception.UserNotFoundException;
import gift.model.product.Product;
import gift.model.user.User;
import gift.model.user.UserRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
