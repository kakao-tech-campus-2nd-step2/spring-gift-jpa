package gift;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<Wish> getWishesByMemberId(Long memberId) {
        return wishRepository.findAllByMemberId(memberId);
    }

    public void addWish(Long memberId, Long productId) {
        Wish wish = new Wish(null, memberId, productId);
        wishRepository.save(wish);
    }

    public void removeWish(Long memberId, Long productId) {
        wishRepository.deleteByMemberIdAndProductId(memberId, productId);
    }
}
