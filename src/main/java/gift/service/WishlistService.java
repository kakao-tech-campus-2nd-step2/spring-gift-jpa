package gift.service;

import gift.repository.WishlistRepository;
import gift.vo.Wish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wish> getWishProductList(Long memberId) {
        return wishlistRepository.findByMemberId(memberId);
    }

    public void addWishProduct(Wish wish) {
        wishlistRepository.save(wish);
    }

    public void deleteWishProduct(Long id) {
        wishlistRepository.deleteById(id);
    }

}
