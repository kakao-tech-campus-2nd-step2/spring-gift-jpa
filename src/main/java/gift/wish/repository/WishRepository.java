package gift.wish.repository;

import gift.user.entity.User;
import gift.product.entity.Product;
import gift.wish.entity.Wish;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
  List<Wish> findByUser(User user);

  void deleteByUserAndProduct(User user, Product product);

  Optional<Wish> findByUserAndProduct(User user, Product product);

  List<Wish> findByProductId(Long productId);
}
