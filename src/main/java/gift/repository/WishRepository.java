package gift.repository;

import gift.model.Wish;
import java.util.List;

public interface WishRepository {
    List<Wish> findByMemberId(Long memberId);
    Wish save(Wish wish);
    boolean deleteByIdAndMemberId(Long id, Long memberId);
}
