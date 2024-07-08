package gift.domain;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class Wishlist {

    @NotNull(message = "유저ID는 필수입니다.")
    private Long UserId;

    @NotNull(message = "상품ID는 필수입니다.")
    private Long ProductId;

    @PositiveOrZero(message = "0개 이상이어야 합니다.")
    private int quantity;

    public Wishlist(Long UserId, Long ProductId, int quantity) {
        this.UserId = UserId;
        this.ProductId = ProductId;
        this.quantity = quantity;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public  Long getProductId() {
        return ProductId;
    }

    public void setProductId(Long productId) {
        ProductId = productId;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
