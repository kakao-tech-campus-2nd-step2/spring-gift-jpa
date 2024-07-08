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

    public void updateProductInWishList(Long memberId, Long productId, int amount) {
        ProductAmount product = findWishListProduct(memberId, productId);

        if (product == null && amount > 0) {
            addProductToWishList(memberId, productId, amount);
            return;
        }
        if (amount > 0) {
            updateExistingItemAmount(memberId, productId, amount);
            return;
        }
        deleteProductInWishList(memberId, productId);
    }

    public void addProductToWishList(Long memberId, Long productId, int amount) {
        wishListRepository.addWishProduct(memberId, productId, amount);
    }

    public Long deleteProductInWishList(Long memberId, Long productId) {
        return wishListRepository.deleteProduct(memberId, productId);
    }

    private ProductAmount findWishListProduct(Long memberId, Long productId) {
        return wishListRepository.getProductByMemberIdAndProductId(memberId, productId);
    }

    private void updateExistingItemAmount(Long memberId, Long productId, int amount) {
        wishListRepository.updateProductInWishList(memberId, productId, amount);
    }

}
