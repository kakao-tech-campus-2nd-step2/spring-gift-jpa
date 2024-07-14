package gift.repository;

import gift.domain.Wish;

import java.util.List;

public interface WishRepository {
    Wish save(Wish wish);

    List<Wish> findByUserId(Long memberId);

    void deleteByUserIdAndProductId(Long userId, Long productId);
}
