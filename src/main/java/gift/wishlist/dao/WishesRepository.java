package gift.wishlist.dao;

import gift.wishlist.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishesRepository extends JpaRepository<Wish, Long> {

    boolean existsByMemberIdAndProductId(Long memberId, Long productId);

    List<Wish> findByMemberId(Long memberId);

}