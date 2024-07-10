package gift.repository.wishlist;

import gift.domain.WishlistItem;
import gift.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WIshlistSpringDataJpaRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByMember(Member member);
}