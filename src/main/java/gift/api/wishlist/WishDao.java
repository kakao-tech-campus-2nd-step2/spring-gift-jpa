package gift.api.wishlist;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class WishDao {

    private final WishRepository wishRepository;

    public WishDao(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<Wish> getAllWishes(Long memberId) {
        return wishRepository.findByMemberId(memberId);
    }

    public void insert(WishRequest wishRequest, Long memberId) {
        wishRepository.save(new Wish(memberId, wishRequest.productId(), wishRequest.quantity()));
    }

    public void update(WishRequest wishRequest, Long memberId) {
        wishRepository.save(new Wish(memberId, wishRequest.productId(), wishRequest.quantity()));
    }

    public void delete(WishRequest wishRequest, Long memberId) {
        wishRepository.deleteByMemberIdAndProductId(memberId, wishRequest.productId());
    }
}
