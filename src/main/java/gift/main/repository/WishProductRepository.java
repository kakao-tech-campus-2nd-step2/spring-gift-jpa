package gift.main.repository;

import gift.main.entity.WishlistProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishProductRepository extends JpaRepository<WishlistProduct, Long> {
    public boolean deleteByProductIdAndUserId(Long productId, Long userId);
    public List<WishlistProduct> findAllByUserId(Long userId);
}
