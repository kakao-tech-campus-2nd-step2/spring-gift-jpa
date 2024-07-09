package gift.repository;

import gift.domain.Wish;

import java.util.List;
import java.util.Optional;

public interface WishRepository {
    Optional<List<Wish>> findByMemberId(Long memberId);
    Optional<Wish> findByIdAndMemberId(Long id, Long memberId);
}
