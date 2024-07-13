package gift.entity;

import gift.validation.constraint.ProductIdConstraint;
import jakarta.validation.constraints.NotNull;

public class WishlistDTO {

    @ProductIdConstraint
    @NotNull
    private Long productId;

    public WishlistDTO() {
    }

    public WishlistDTO(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
