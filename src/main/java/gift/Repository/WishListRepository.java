package gift.Repository;

import gift.DTO.WishList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WishListRepository extends JpaRepository<WishList, Long> {

  @Query("SELECT wl from WishList wl join fetch wl.member")
  List<WishList> findByMemberId(Long memberId);

  @Query("SELECT wl from WishList wl join fetch wl.product")
  List<WishList> findByProductId(Long productId);
}
