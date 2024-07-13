package gift.wish.repository;

import gift.global.MyCrudRepository;
import gift.wish.domain.Wish;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends MyCrudRepository<Wish, Long> {
    List<Wish> findAllByMemberId(Long memberId);

    Optional<Wish> findByMemberIdAndProductId(Long memberId, Long productId);
}
