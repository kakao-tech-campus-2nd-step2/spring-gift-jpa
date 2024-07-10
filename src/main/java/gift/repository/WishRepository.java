package gift.repository;

import gift.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {

    Optional<Wish> findByMemberIdAndProductId(Long memberId, Long productId);

    List<Wish> findAllByMemberIdOrderByCreatedAtAsc(Long memberId);

    void deleteByProductIdAndMemberId(Long productId, Long memberId);

    boolean existsByProductIdAndMemberId(Long productId, Long memberId);
}
