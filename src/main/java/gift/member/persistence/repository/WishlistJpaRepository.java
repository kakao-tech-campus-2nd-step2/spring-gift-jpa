package gift.member.persistence.repository;

import gift.member.persistence.entity.Wishlist;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistJpaRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByMemberId(Long memberId);

    Optional<Wishlist> findByMemberIdAndProductId(Long memberId, Long productId);

    void deleteByMemberIdAndProductId(Long memberId, Long productId);
}
