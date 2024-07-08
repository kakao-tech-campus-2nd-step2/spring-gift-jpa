package gift.service;

import gift.domain.WishList;
import gift.repository.user.UserRepository;
import gift.repository.wish.WishListRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public List<WishList> getWishListByUserId(Long userId) {
        return wishListRepository.findByUserId(userId);
    }

    public void addWishList(Long userId, Long productId, int quantity) {
        WishList wishList = new WishList(userId, productId, quantity);
        wishListRepository.save(wishList);
    }

    public void updateProductQuantity(Long id, int quantity) {
        wishListRepository.updateQuantity(id, quantity);
    }


    public void removeWishList(Long productId) {
        wishListRepository.deleteById(productId);
    }
}
