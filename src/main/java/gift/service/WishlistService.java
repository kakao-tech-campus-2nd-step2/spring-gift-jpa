package gift.service;

import gift.model.WishList;
import gift.repository.WishlistRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public WishList addProduct(WishList product) {
        return wishlistRepository.save(product);
    }

    public List<WishList> getProductsByMemberId(Long memberId) {
        return wishlistRepository.findByMemberId(memberId);
    }

    public void deleteById(Long productId) {
        wishlistRepository.deleteById(productId);
    }
}
