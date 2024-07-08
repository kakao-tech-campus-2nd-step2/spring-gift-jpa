package gift.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Wish {
    @NotNull
    private String email;
    @NotNull
    private Long productId;
    @NotNull
    @Min(1)
    private Long quantity;

    public Wish(String email, Long productId,Long quantity) {
        this.email = email;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

}
