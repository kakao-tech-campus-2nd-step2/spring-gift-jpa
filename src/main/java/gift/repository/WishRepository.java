package gift.repository;

import gift.domain.Wish;
import gift.dto.WishRequestDto;
import java.util.List;
import java.util.Optional;

public interface WishRepository {

    Optional<List<Wish>> findById(Long id);
    void addWish(Wish wish);
    void deleteWish(Long userId, Long productId);
    void updateWish(Long userId, Long productId, int quantity);
}
