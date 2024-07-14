package gift.doamin.wishlist.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class WishForm {

    @NotNull
    private Long productId;
    @PositiveOrZero
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public boolean isZeroQuantity() {
        return quantity == null || quantity.equals(0);
    }
}
