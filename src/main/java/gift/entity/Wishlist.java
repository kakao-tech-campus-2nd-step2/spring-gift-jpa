package gift.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class Wishlist {

    @NotBlank(message = "유저 Id는 필수 입력 값입니다.")
    private Long memberId;

    @NotBlank(message = "상품 Id는 필수 입력 값입니다.")
    private Long productId;

    @PositiveOrZero(message = "수량은 0개 이상이어야 합니다.")
    private int quantity;

    public Wishlist(Long memberId, Long productId, int quantity) {
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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
