package gift.service;

import gift.dto.response.WishProductResponse;
import gift.entity.Wish;
import gift.exception.WishAlreadyExistsException;
import gift.exception.WishNotFoundException;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    private final WishRepository wishListRepository;

    public WishListService(WishListRepository wIshListRepository) {
        this.wishListRepository = wIshListRepository;
    public WishListService(WishRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
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

    public List<WishProductResponse> getWishProductsByMemberId(Long memberId) {
        return wishListRepository.findWishesByMemberIdWithProduct(memberId)
                .stream()
                .map(wish -> new WishProductResponse(wish.getProductId(), wish.getProduct().getName(), wish.getProduct().getPrice(), wish.getProduct().getImageUrl(), wish.getAmount()))
                .toList();
    }

}
