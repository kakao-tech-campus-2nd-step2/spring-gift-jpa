package gift.api.wishlist;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final EntityManager entityManager;
    private final WishRepository wishRepository;

    public WishService(EntityManager entityManager, WishRepository wishRepository) {
        this.entityManager = entityManager;
        this.wishRepository = wishRepository;
    }

    public List<Wish> getItems(Long memberId) {
        return wishRepository.findByMemberId(memberId);
    }

    public void add(Long memberId, WishRequest wishRequest) {
        wishRepository.save(new Wish(memberId, wishRequest.productId(), wishRequest.quantity()));
    }

    @Transactional
    public void update(Long memberId, WishRequest wishRequest) {
        if (wishRequest.quantity() == 0) {
            wishRepository.deleteByMemberIdAndProductId(memberId, wishRequest.productId());
            return;
        }
        Wish wish = entityManager.find(Wish.class, new WishId(memberId, wishRequest.productId()));
        wish.setQuantity(wishRequest.quantity());
    }

    public void delete(Long memberId, WishRequest wishRequest) {
        wishRepository.deleteByMemberIdAndProductId(memberId, wishRequest.productId());
    }
}
