package gift.repository;

import gift.domain.Wish;

import java.util.List;
import java.util.Optional;

public interface WishRepository {
    Optional<Wish> findById(Long id);
    List<Wish> findByMemberId(Long memberId);
    List<Wish> findByProductId(Long productId);
    Wish save(Wish wish);
    void delete(Wish wish);
    List<Wish> findByUserId(Long memberId);
    void deleteByUserIdAndProductId(Long userId, Long productId);
}
