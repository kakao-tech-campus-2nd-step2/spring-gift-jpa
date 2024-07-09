package gift.wishlist.model.dto;

import gift.product.model.dto.ProductResponse;

public class WishListResponse {
    private Long wishId;
    private ProductResponse product;
    private int quantity;

    public WishListResponse(Long wishId, Long productId, String productName, int productPrice, String productImageUrl,
                            int quantity) {
        this.wishId = wishId;
        this.product = new ProductResponse(productId, productName, productPrice, productImageUrl);
        this.quantity = quantity;
    }

    public Long getWishId() {
        return wishId;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}