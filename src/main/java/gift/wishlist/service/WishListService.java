package gift.wishlist.service;

import gift.wishlist.model.WishListRepository;
import gift.wishlist.model.dto.AddWishRequest;
import gift.wishlist.model.dto.Wish;
import gift.wishlist.model.dto.WishListResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    @Transactional(readOnly = true)
    public List<WishListResponse> getWishList(Long userId) {
        List<WishListResponse> wishes = wishListRepository.findWishesByUserId(userId);
        if (wishes.isEmpty()) {
            throw new EntityNotFoundException("WishList");
        }
        return wishes;
    }

    @Transactional
    public void addWish(Long userId, AddWishRequest addWishRequest) {
        Wish wish = new Wish(userId, addWishRequest.productId(), addWishRequest.quantity());
        wishListRepository.save(wish);
    }

    @Transactional
    public void updateWishQuantity(Long userId, Long wishId, int quantity) {
        Wish wish = wishListRepository.findByIdAndUserIdAndIsActiveTrue(wishId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Wish"));
        wish.setQuantity(quantity);
        wishListRepository.save(wish);
    }

    @Transactional
    public void deleteWish(Long userId, Long wishId) {
        Wish wish = wishListRepository.findByIdAndUserIdAndIsActiveTrue(wishId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Wish"));
        wish.setActive(false);
        wishListRepository.save(wish);
    }
}
