package gift.service;

import gift.dto.ProductAmount;
import gift.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wIshListRepository) {
        this.wishListRepository = wIshListRepository;
    }

    public List<ProductAmount> getProductIdsAndAmount(Long memberId) {
        return wishListRepository.getWishListProductIdsByMemberId(memberId);
    }

    public boolean addProductToWishList(Long memberId, Long productId, int amount) {
        boolean isAlreadyExist = isAlreadyExistProduct(memberId, productId);
        if (isAlreadyExist) {
            return false;
        }
        wishListRepository.addWishProduct(memberId, productId, amount);
        return true;
    }

    public boolean deleteProductInWishList(Long memberId, Long productId) {
        boolean isAlreadyExist = isAlreadyExistProduct(memberId, productId);
        if (!isAlreadyExist) {
            return false;
        }
        wishListRepository.deleteProduct(memberId, productId);
        return true;
    }

    public boolean isAlreadyExistProduct(Long memberId, Long productId) {
        return wishListRepository.isAlreadyExistProduct(memberId, productId);
    }

    public boolean updateWishList(Long memberId, Long productId, int amount) {
        boolean isAlreadyExist = isAlreadyExistProduct(memberId, productId);
        if (!isAlreadyExist) {
            return false;
        }
        wishListRepository.updateProductInWishList(memberId, productId, amount);
        return true;
    }

}
