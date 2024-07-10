package gift.repository;

import gift.model.product.Product;
import gift.model.user.User;
import gift.model.wish.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

    boolean existsByProductAndUser(Product product, User user);

    List<Wish> findByUser(User user);

    void deleteByProductAndUser(Product product, User user);

    Optional<Wish> findByProductAndUser(Product product, User user);

    void deleteByProduct(Product product);
}
