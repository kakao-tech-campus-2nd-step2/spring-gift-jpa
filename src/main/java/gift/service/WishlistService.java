package gift.service;

import gift.model.WishList;
import gift.repository.WishlistRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public WishList addProduct(WishList product) {
        return wishlistRepository.addProduct(product);
    }

    public List<WishList> getProductsByMemberId(Long memberId) {
        return wishlistRepository.getProductsByMemberId(memberId);
    }

    public void deleteItem(Long id) {
        wishlistRepository.deleteItem(id);
    }
}
