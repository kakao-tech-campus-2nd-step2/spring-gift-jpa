package gift.model;

import gift.util.ProductIdConstraint;
import jakarta.validation.constraints.NotNull;

public class WishListDTO {

    @ProductIdConstraint
    @NotNull
    private Long productId;

    public WishListDTO() {
    }

    public WishListDTO(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
