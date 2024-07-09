package gift.domain.dto;

import gift.domain.entity.Product;

public record WishResponseDto(
    Long productId,
    String productName,
    Long productPrice,
    String productImageUrl,
    Long quantity) {

    public static WishResponseDto of(Long quantity, Product product) {
        return new WishResponseDto(product.id(), product.name(), product.price(), product.imageUrl(), quantity);
    }
}
