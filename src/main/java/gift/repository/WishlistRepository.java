package gift.repository;

import gift.entity.Wishlist;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Page<Wishlist> findByMemberEmail(String email, Pageable pageable);


    Wishlist findByMemberEmailAndProductId(String email, Long productId);
}

