package gift.wish.repository;

import gift.user.entity.User;
import gift.product.entity.Product;
import gift.wish.entity.Wish;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {

  List<Wish> findByUser(User user);

  void deleteByUserAndProduct(User user, Product product);

  Optional<Wish> findByUserAndProduct(User user, Product product);

  List<Wish> findByProductId(Long productId);

  Page<Wish> findByUser(User user, Pageable pageable);

  @Transactional
  @Modifying
  @Query("DELETE FROM Wish w WHERE w.product.id = :productId")
  void deleteAllByProductId(Long productId);
}
