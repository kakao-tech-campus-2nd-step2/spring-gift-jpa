package gift.product.dao;

import gift.product.model.Wish;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface WishListDao extends JpaRepository<Wish, Long> {
    Collection<Wish> findAllByMemberId(Long memberId);
}
