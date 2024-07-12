package gift.service;

import gift.domain.Wish;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService{
    private final WishRepository wishRepository;
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<Wish> getWishList(Long memberId) {
        return wishRepository.findByUserId(memberId);
    }

    public void addToWishList(Long userId, Long productId) {
        Wish wish = new Wish();
        wish.setMember(userId);
        wish.setProduct(productId);
        wishRepository.save(wish);
    }

    public void removeFromWishList(Long userId, Long productId) {
        wishRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
