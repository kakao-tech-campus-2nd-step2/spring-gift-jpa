package gift.domain.dto.response;

import gift.domain.entity.Product;

public record WishResponse(
    Long productId,
    String productName,
    Long productPrice,
    String productImageUrl,
    Long quantity) {

    public static WishResponse of(Long quantity, Product product) {
        return new WishResponse(product.id(), product.name(), product.price(), product.imageUrl(), quantity);
    }
}
