package gift.service;

import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.entity.Wish;
import gift.entity.WishDao;
import gift.exception.BusinessException;
import gift.exception.ErrorCode;
import gift.dto.ProductResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishService {
    private final WishDao wishDao;
    private final ProductService productService;

    public WishService(WishDao wishDao, ProductService productService) {
        this.wishDao = wishDao;
        this.productService = productService;
    }

    public WishResponseDto addWish(Long userId, WishRequestDto wishRequestDto) {
        validateProductId(wishRequestDto.productId);
        Wish wish = new Wish(userId, wishRequestDto.productId);
        Wish createdWish = wishDao.insertWish(wish);

        ProductResponseDto product = productService.getProductById(wishRequestDto.productId);

        return toWishResponseDto(createdWish, product);
    }

    public List<WishResponseDto> getWishesByUserId(Long userId) {
        List<Wish> wishes = wishDao.selectWishesByUserId(userId);
        return wishes.stream()
                .map(this::toWishResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteWish(Long wishId) {
        wishDao.selectWish(wishId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, "ID: " + wishId));
        wishDao.deleteWish(wishId);
    }

    private void validateProductId(Long productId) {
        productService.getProductById(productId); // 유효하지 않으면 예외 발생
    }

    private WishResponseDto toWishResponseDto(Wish wish) {
        ProductResponseDto product = productService.getProductById(wish.productId);
        return new WishResponseDto(wish.id, product.id, product.name, product.price, product.imageUrl);
    }

    private WishResponseDto toWishResponseDto(Wish wish, ProductResponseDto product) {
        return new WishResponseDto(wish.id, product.id, product.name, product.price, product.imageUrl);
    }
}
