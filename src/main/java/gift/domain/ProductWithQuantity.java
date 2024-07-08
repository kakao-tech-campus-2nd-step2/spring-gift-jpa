package gift.domain;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductWithQuantity {

    @NotNull
    private long productId;
    @NotNull
    private int quantity;

    public ProductWithQuantity() {
    }

    public ProductWithQuantity(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;

    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }


}
