package gift.model.repository;

import gift.model.Wish;
import java.util.List;
import java.util.Optional;

public interface WishRepository extends RepositoryInterface<Wish, Long> {
    Optional<Wish> findByIdAndUserId(Long id, Long userId);

    List<Wish> findWishesByUserId(Long userId);
}
