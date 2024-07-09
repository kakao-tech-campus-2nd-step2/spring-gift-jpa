package gift.product.dao;

import gift.product.model.WishProduct;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface WishListDao extends JpaRepository<WishProduct, Long> {
    Collection<WishProduct> findAllByMemberId(Long memberId);
}
