package gift.service;

import gift.entity.Wish;
import gift.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;

    @Autowired
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<Wish> getWishlist(Long memberId) {
        return wishRepository.findByMemberId(memberId);
    }

    public Wish addWishlist(Wish wish) {
        Optional<Wish> wishlists = wishRepository.findByMemberIdAndProductId(wish.getMemberId(),
            wish.getProductId());
        if (wishlists.isPresent()) {
            Wish existingWish = wishlists.get();
            existingWish.setQuantity(wish.getQuantity());
            return wishRepository.save(existingWish);
        }
        return wishRepository.save(wish);
    }

    public void deleteById(Long memberId, Long productId) {
        Optional<Wish> wish = wishRepository.findByMemberIdAndProductId(memberId, productId);
        wishRepository.delete(wish.get());
    }
}