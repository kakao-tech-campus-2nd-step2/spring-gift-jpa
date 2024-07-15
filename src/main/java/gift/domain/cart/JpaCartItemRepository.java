package gift.domain.cart;

import gift.domain.product.Product;
import gift.domain.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByUserId(Long userId);

    void deleteByUserIdAndProductId(Long userId, Long productId);

    boolean existsByUserAndProduct(User user, Product product);

    List<CartItem> findAllByUser(User user);

    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);

    // paging
    Page<CartItem> findAllByUserId(Long userId, PageRequest pageRequest);

}
