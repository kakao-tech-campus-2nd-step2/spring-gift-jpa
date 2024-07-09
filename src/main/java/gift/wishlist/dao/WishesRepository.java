package gift.wishlist.dao;

import gift.wishlist.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishesRepository extends JpaRepository<Wish, Long> {

    boolean existsByMemberIdAndProductId(Long memberId, Long productId);

    List<Wish> findByMemberId(Long memberId);

}