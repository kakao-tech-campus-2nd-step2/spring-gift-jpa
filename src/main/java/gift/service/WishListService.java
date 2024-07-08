package gift.service;

import gift.repository.WishListRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishListService {
    @Autowired
    private WishListRepository wishListRepository;

    public List<Long> readWishList(Long userId) {
        return  wishListRepository.readWishList(userId);
    }

    public void addProductToWishList(Long userId, Long productId) {
        wishListRepository.addProductToWishList(userId, productId);
    }

    public void removeWishList(Long userId) {
        wishListRepository.removeWishList(userId);
    }

    public void removeProductFromWishList(Long userId, Long productId) {
        wishListRepository.removeProductFromWishList(userId, productId);
    }

}
