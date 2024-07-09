package gift.service;

import gift.domain.WishList;
import gift.repository.wish.WishListRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public Optional<WishList> getWishListByUserId(Long userId) {
        return wishListRepository.findById(userId);
    }

    public void addWishList(Long userId, Long productId, int quantity) {
        WishList wishList = new WishList(userId, productId, quantity);
        wishListRepository.save(wishList);
    }

    public void updateProductQuantity(Long id, int quantity) {
        Optional<WishList> existingWish = wishListRepository.findById(id);
        WishList wish = existingWish.get();
        wish.setQuantity(quantity);

        wishListRepository.save(wish);
    }


    public void removeWishList(Long productId) {
        wishListRepository.deleteById(productId);
    }
}
