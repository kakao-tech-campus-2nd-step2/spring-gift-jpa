package gift.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gift.entity.WishList;

@Repository
public interface WishListRepository  extends JpaRepository<WishList, Long>{

    List<WishList> findByMemberId(Long memberId);
    List<WishList> findByProductId(Long productId);

    Long findIdByMemberIdAndProductId(Long memberId, Long productId);
}
