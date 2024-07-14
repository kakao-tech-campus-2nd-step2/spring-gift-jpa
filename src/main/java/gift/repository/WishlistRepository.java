package gift.repository;

import gift.domain.Wishlist;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    @Query("SELECT w FROM Wishlist w WHERE w.member.email = ?1 AND w.product.id = ?2")
    Optional<Wishlist> findByMember_EmailAndProduct_Id(String email, Long productId);

    Page<Wishlist> findByMember_Email(String email, Pageable pageable);
}
