package gift.repository;

import gift.domain.WishList.WishList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    List<WishList> findByMemberId(Long memberId);

    Optional<WishList> findByMemberIdAndProductId(Long MemberId, Long ProductId);
}
