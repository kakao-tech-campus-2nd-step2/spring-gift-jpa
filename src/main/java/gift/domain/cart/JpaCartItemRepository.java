package gift.domain.cart;

import gift.domain.product.Product;
import gift.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByUserId(Long userId);

    void deleteByUserIdAndProductId(Long userId, Long productId);

    boolean existsByUserAndProduct(User user, Product product);

    List<CartItem> findAllByUser(User user);
}
