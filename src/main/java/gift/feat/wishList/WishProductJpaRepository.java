package gift.feat.wishList;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishProductJpaRepository extends JpaRepository<WishProduct, Long> {
	WishProduct findByProductIdAndUserId(Long productId, Long userId);
	List<WishProduct> findByUserId(Long userId);
}
