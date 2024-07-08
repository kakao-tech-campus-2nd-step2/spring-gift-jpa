package gift.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class WishlistRequestDto {
    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;

    @PositiveOrZero(message = "0개 이상이어야 합니다.")
    private int quantity;

    public WishlistRequestDto(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
