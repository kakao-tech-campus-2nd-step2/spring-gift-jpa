package gift.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    List<WishList> findByMemberId(Long memberId);

    void deleteByMemberIdAndProductId(Long memberId, Long productId);
}
