package gift.service;

import gift.model.WishlistItem;
import gift.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<WishlistItem> getWishlistByUserId(Long userId) {
        return wishlistRepository.findListByUserId(userId);
    }
    public List<WishlistItem> saveWishlistItems(List<WishlistItem> wishlistItems) {
        return wishlistRepository.saveAll(wishlistItems);
    }
}
