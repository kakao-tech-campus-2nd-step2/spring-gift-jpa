package gift.service;

import gift.model.WishList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface WishListRepository extends JpaRepository<WishList, Long> {

    Optional<List<WishList>> findByMemberId(long memberId);

    Optional<WishList> findByMemberIdAndProductName(long memberId, String productName);
}