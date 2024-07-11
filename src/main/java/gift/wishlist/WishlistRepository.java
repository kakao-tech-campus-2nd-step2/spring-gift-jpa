package gift.wishlist;

import gift.member.Member;
import gift.product.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findAllByMember(Member member);

    boolean existsByMemberAndProduct(Member member, Product product);
}
