package gift.api.wishlist;

import gift.api.member.Member;
import gift.api.product.Product;
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
        Member member = entityManager.find(Member.class, memberId);
        Product product = entityManager.find(Product.class, wishRequest.productId());
        wishRepository.save(new Wish(member, product, wishRequest.quantity()));
    }

    @Transactional
    public void update(Long memberId, WishRequest wishRequest) {
        Wish wish = entityManager.find(Wish.class, new WishId(memberId, wishRequest.productId()));
        wish.updateQuantity(wishRequest.quantity());
    }

    public void delete(Long memberId, WishRequest wishRequest) {
        wishRepository.deleteById(new WishId(memberId, wishRequest.productId()));
    }
}
