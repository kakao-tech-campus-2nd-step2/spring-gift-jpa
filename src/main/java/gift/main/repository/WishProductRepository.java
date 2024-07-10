package gift.main.repository;

import gift.main.entity.WishProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishProductRepository extends JpaRepository<WishProduct, Long> {
    void deleteByProductIdAndUserId(Long productId, Long userId);
    List<WishProduct> findAllByUserId(Long userId);
}
