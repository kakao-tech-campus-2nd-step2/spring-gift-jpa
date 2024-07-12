package gift.service;

import gift.model.WishlistItem;
import gift.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Page<WishlistItem> getWishlistByUserId(Long userId, Pageable pageable) {
        return wishlistRepository.findListByUserId(userId, pageable);
    }
    public List<WishlistItem> saveWishlistItems(List<WishlistItem> wishlistItems) {
        return wishlistRepository.saveAll(wishlistItems);
    }
}
