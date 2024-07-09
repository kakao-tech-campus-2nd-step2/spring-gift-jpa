package gift.service;

import gift.dto.response.WishProductResponse;
import gift.entity.Wish;
import gift.exception.WishAlreadyExistsException;
import gift.exception.WishNotFoundException;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    private final WishRepository wishListRepository;
    private final ProductRepository productRepository;

    public WishListService(WishRepository wishListRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
    }


    public void addProductToWishList(Long memberId, Long productId, int amount) {
        if (wishListRepository.findByMemberIdAndProductId(memberId, productId).isPresent()) {
            throw new WishAlreadyExistsException("위시리스트에 이미 추가된 상품");
        }
        Wish wish = new Wish(memberId, amount, productRepository.findById(productId).get());
        wishListRepository.save(wish);
    }

    public void deleteProductInWishList(Long memberId, Long productId) {
        Wish wish = wishListRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new WishNotFoundException("위시리스트에 없는 상품"));
        wishListRepository.delete(wish);
    }

    public void updateWishList(Long memberId, Long productId, int amount) {
        Wish wish = wishListRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new WishNotFoundException("요청한 위시는 존재하지 않습니다."));
        wish.setAmount(amount);

        wishListRepository.save(wish);
    }

    public List<WishProductResponse> getWishProductsByMemberId(Long memberId) {
        return wishListRepository.findAllByMemberIdWithProduct(memberId)
                .stream()
                .map(wish -> new WishProductResponse(wish.getProduct().getId(), wish.getProduct().getName(), wish.getProduct().getPrice(), wish.getProduct().getImageUrl(), wish.getAmount()))
                .toList();
    }

}
