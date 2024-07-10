package gift.Repository;

import gift.Model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByMemberId(Long memberId);
    Wish findByMemberIdAndProductId(Long memberId, Long productId);
}
