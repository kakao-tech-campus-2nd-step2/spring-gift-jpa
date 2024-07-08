package gift.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WishRequestDto {
    private final Long productId;

    @JsonCreator
    public WishRequestDto(@JsonProperty("productId") Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
