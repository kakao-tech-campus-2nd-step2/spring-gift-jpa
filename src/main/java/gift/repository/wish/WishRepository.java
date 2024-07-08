package gift.repository.wish;

import gift.domain.Wish;

import java.util.List;
import java.util.Optional;

public interface WishRepository {

    Optional<Wish> findById(Long wishId);

    Integer findWishCountByWishIdAndMemberEmail(Long likesId, String email);
    List<Wish> findWishByMemberEmail(String email);

    Long wishSave(Long productId, String email, int count);

    Long updateWish(Long likesId, int count);

    Long deleteWish(Long likesId);
}
