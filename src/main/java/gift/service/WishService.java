package gift.service;

import gift.dto.ProductResponseDto;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.entity.Wish;
import gift.exception.BusinessException;
import gift.exception.ErrorCode;
import gift.mapper.WishMapper;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductService productService;

    public WishService(WishRepository wishRepository, ProductService productService) {
        this.wishRepository = wishRepository;
        this.productService = productService;
    }

    public WishResponseDto addWish(Long userId, WishRequestDto wishRequestDto) {
        ProductResponseDto product = productService.getProductById(wishRequestDto.getProductId());
        Wish wish = new Wish(userId, wishRequestDto.getProductId());
        Wish createdWish = wishRepository.save(wish);

        return WishMapper.toWishResponseDto(createdWish, product);
    }


    public List<WishResponseDto> getWishesByUserId(Long userId) {
        List<Wish> wishes = wishRepository.findByUserId(userId);
        List<Long> productIds = wishes.stream()
                .map(Wish::getProductId)
                .collect(Collectors.toList());

        List<ProductResponseDto> products = productService.getProductsByIds(productIds);
        Map<Long, ProductResponseDto> productMap = products.stream()
                .collect(Collectors.toMap(ProductResponseDto::getId, product -> product));

        return wishes.stream()
                .map(wish -> {
                    ProductResponseDto product = productMap.get(wish.getProductId());
                    return WishMapper.toWishResponseDto(wish, product);
                })
                .collect(Collectors.toList());
    }


    public void deleteWish(Long wishId) {
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, "ID: " + wishId));
        wishRepository.delete(wish);
    }

    private void validateProductId(Long productId) {
        productService.getProductById(productId);
    }
}
