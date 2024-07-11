package gift.wish.repository;

import gift.wish.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByMemberId(Long memberId);
    Optional<Wish> findByMemberIdAndProductId(Long memberId, Long productId);
}
