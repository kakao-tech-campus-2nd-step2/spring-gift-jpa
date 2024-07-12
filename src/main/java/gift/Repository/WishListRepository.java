package gift.Repository;

import gift.DTO.WishList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {

  List<WishList> findByMemberId(Long memberId);

  List<WishList> findByProductId(Long productId);
}
