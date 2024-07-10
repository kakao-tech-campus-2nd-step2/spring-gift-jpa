package gift.repository;

import gift.domain.Wish;
import gift.dto.WishRequestDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish,Long> {
    Optional<List<Wish>> findByMemberId(Long memberId);
    Optional<Wish> findByMemberIdAndProductId(Long memberId, Long productId);
}
