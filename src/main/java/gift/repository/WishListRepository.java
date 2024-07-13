package gift.repository;

import gift.model.WishList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

    List<WishList> findWishListByMemberId(long memberId);

    Optional<WishList> findByMemberIdAndProductId(long memberId, long productId);
}