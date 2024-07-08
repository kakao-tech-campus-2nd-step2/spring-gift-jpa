package gift.service;

import gift.domain.model.ProductDto;
import gift.domain.model.WishResponseDto;
import gift.domain.model.WishUpdateRequestDto;
import gift.domain.repository.WishListRepository;
import gift.exception.DuplicateWishItemException;
import gift.exception.NoSuchWishException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductService productService;

    public WishListService(WishListRepository wishListRepository, ProductService productService) {
        this.wishListRepository = wishListRepository;
        this.productService = productService;
    }

    public List<WishResponseDto> getProductsByUserEmail(String email) {
        return wishListRepository.getProductsByUserEmail(email);
    }

    public ProductDto addWish(String email, Long productId) {
        productService.validateExistProductId(productId);
        if (wishListRepository.isExistWish(email, productId)) {
            throw new DuplicateWishItemException("이미 위시리스트에 존재하는 상품입니다.");
        }
        wishListRepository.addWish(email, productId);
        return productService.getProduct(productId);
    }

    public void deleteWishProduct(String email, Long productId) {
        productService.validateExistProductId(productId);
        validateExistWishProduct(email, productId);
        wishListRepository.deleteWishProduct(email, productId);
    }

    public void updateWishProduct(String email, WishUpdateRequestDto wishUpdateRequestDto) {
        productService.validateExistProductId(wishUpdateRequestDto.getProductId());
        validateExistWishProduct(email, wishUpdateRequestDto.getProductId());
        wishListRepository.updateWishProduct(email, wishUpdateRequestDto);
    }

    public void validateExistWishProduct(String email, Long productId){
        if (!wishListRepository.isExistWish(email, productId)) {
            throw new NoSuchWishException("위시리스트에 존재하지 않는 상품입니다.");
        }
    }
}
