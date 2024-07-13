package gift.main.repository;

import gift.main.entity.WishProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishProductRepository extends JpaRepository<WishProduct, Long> {
    void deleteByProductIdAndUserId(Long productId, Long userId);
    boolean existsByProductIdAndUserId(Long productId, Long userId);

    Optional<List<WishProduct>> findAllByUserId(Long userId);
}
