package gift.wish.repository;

import gift.global.MyCrudRepository;
import gift.wish.domain.Wish;

import java.util.List;

public interface WishRepository extends MyCrudRepository<Wish, Long> {
    List<Wish> findAllByMemberId(Long memberId);
}
