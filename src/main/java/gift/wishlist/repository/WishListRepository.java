package gift.wishlist.repository;

import gift.wishlist.entity.WishList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    List<WishList> findAllByMemberId(Long memberId);

    Optional<WishList> findByIdAndMemberId(Long id, Long memberId);

    Optional<WishList> findByMemberIdAndProductId(Long memberId, Long productId);

    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
