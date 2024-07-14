package gift.domain.wish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    void deleteByMemberIdAndProductId(Long memberId, Long productId);

    Optional<Wish> findByMemberIdAndProductId(Long memberId, Long productId);

    List<Wish> findAllByMemberId(Long id);
}
