package gift.repository.wish;

import gift.domain.wish.Wish;

import java.util.List;
import java.util.Optional;

public interface WishRepository {
    void save(Wish wish);
    Optional<Wish> findByIdAndUserId(Long wishId, Long userId);
    List<Wish> findByUserId(Long userId);
    void deleteById(Long wishId);
}
