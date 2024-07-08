package gift.wishlist.service;

import gift.wishlist.model.WishListRepository;
import gift.wishlist.model.dto.AddWishRequest;
import gift.wishlist.model.dto.WishListResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public List<WishListResponse> getWishList(Long userId) {
        List<WishListResponse> wishList = wishListRepository.findWishesByUserId(userId);
        if (wishList.isEmpty()) {
            throw new IllegalArgumentException("해당 사용자의 위시리스트가 존재하지 않습니다.");
        }
        return wishList;
    }

    public void addWish(Long userId, AddWishRequest addWishRequest) {
        int result = wishListRepository.addWish(userId, addWishRequest);
        if (result == 0) {
            throw new IllegalArgumentException("해당 상품을 위시리스트에 추가할 수 없습니다.");
        }
    }

    public void updateWishQuantity(Long userId, Long wishId, int quantity) {
        int result = wishListRepository.updateWishQuantity(userId, wishId, quantity);
        if (result == 0) {
            throw new IllegalArgumentException("해당 위시리스트가 존재하지 않거나 수정할 수 없습니다.");
        }
    }

    public void deleteWish(Long userId, Long wishId) {
        int result = wishListRepository.deleteWish(userId, wishId);
        if (result == 0) {
            throw new IllegalArgumentException("해당 위시리스트가 존재하지 않거나 삭제할 수 없습니다.");
        }
    }
}
