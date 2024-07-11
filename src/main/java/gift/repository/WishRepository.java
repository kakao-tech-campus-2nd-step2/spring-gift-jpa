package gift.repository;

import gift.model.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {

    Optional<Wish> findByMemberIdAndProductId(Long memberId, Long productId);

    Page<Wish> findAllByMemberIdOrderByCreatedAtAsc(Long memberId, Pageable pageable);

    void deleteByProductIdAndMemberId(Long productId, Long memberId);

    boolean existsByProductIdAndMemberId(Long productId, Long memberId);
}
