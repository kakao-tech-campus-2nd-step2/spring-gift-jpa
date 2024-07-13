package gift.wishlist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import gift.product.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WishlistRepository extends JpaRepository<Wishlist,Long>,
    PagingAndSortingRepository<Wishlist, Long> {

    List<Wishlist> findByProductId(Long productId);

    List<Wishlist> findByMemberId(Long memberId);

    Page<Wishlist> findAll(Pageable pageable);

}
