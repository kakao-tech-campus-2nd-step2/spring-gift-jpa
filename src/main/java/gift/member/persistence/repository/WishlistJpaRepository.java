package gift.member.persistence.repository;

import gift.member.persistence.entity.Wishlist;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistJpaRepository extends JpaRepository<Wishlist, Long> {

    Page<Wishlist> findByMemberId(Long memberId, PageRequest pageRequest);

    Optional<Wishlist> findByMemberIdAndProductId(Long memberId, Long productId);

    void deleteByMemberIdAndProductId(Long memberId, Long productId);
}
