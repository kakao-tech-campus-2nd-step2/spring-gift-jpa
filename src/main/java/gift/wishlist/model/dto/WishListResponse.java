package gift.wishlist.model.dto;

import gift.product.model.dto.ProductResponse;

public record WishListResponse(Long wishId, ProductResponse product, int quantity) {
}
