package gift.web.dto.request.wishproduct;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateWishProductRequest {

    @NotNull
    private Long wishProductId;
    @Min(1)
    private Integer quantity;

    public UpdateWishProductRequest(Long wishProductId, Integer quantity) {
        this.wishProductId = wishProductId;
        this.quantity = quantity;
    }

    public Long getWishProductId() {
        return wishProductId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
