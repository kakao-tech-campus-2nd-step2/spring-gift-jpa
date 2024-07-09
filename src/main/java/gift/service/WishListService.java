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

    public WishListService(WishRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }


    public void addProductToWishList(Long memberId, Long productId, int amount) {
        wishListRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new WishAlreadyExistsException("위시리스트에 이미 추가된 상품"));
        Wish wish = new Wish(memberId, productId, amount);
        wishListRepository.save(wish);
    }

    public boolean deleteProductInWishList(Long memberId, Long productId) {
        boolean isAlreadyExist = isAlreadyExistProduct(memberId, productId);
        if (!isAlreadyExist) {
            return false;
        }
        wishListRepository.deleteProduct(memberId, productId);
        return true;
    }

    public void updateWishList(Long memberId, Long productId, int amount) {
        Wish wish = wishListRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new WishNotFoundException("요청한 위시는 존재하지 않습니다."));
        wish.setMemberId(memberId);
        wish.setProductId(productId);
        wish.setAmount(amount);

        wishListRepository.save(wish);
    }

    public List<WishProductResponse> getWishProductsByMemberId(Long memberId) {
        return wishListRepository.findWishesByMemberIdWithProduct(memberId)
                .stream()
                .map(wish -> new WishProductResponse(wish.getProductId(), wish.getProduct().getName(), wish.getProduct().getPrice(), wish.getProduct().getImageUrl(), wish.getAmount()))
                .toList();
    }

}
