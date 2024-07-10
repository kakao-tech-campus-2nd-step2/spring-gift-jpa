package gift.wishlist;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {

//    List<Wishlist> findAllByMemberId(Long memberId);
//
//    Optional<Long> findWishlistBy(Long productId);
//
//    Optional<Long> findBy(Long productId);

    //Optional<Long> findPrdocutIdByProductId(Long productId);

    List<Wishlist> findByProductId(Long productId);

    void deleteByProductId(Long productId);


}
