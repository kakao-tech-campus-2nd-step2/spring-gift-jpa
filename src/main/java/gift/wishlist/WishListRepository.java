package gift.wishlist;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    List<WishList> findAllByEmail(String email);

    WishList findByEmailAndProductId(String email, long productId);

    Boolean existsByEmailAndProductId(String email, long productId);

    void deleteByEmailAndProductId(String email, long productId);
}
