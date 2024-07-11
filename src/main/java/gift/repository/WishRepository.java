package gift.repository;

import gift.model.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findAllByMemberId(Long memberId);

    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
