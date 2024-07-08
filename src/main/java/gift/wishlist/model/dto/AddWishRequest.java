package gift.wishlist.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddWishRequest {
    @NotNull
    private Long productId;
    @NotNull
    @Min(value = 1, message = "상품 수량은 1 이상의 정수로 입력해주세요.")
    private int quantity;

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
