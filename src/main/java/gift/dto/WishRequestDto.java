package gift.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WishRequestDto {
    public final Long productId;

    @JsonCreator
    public WishRequestDto(@JsonProperty("productId") Long productId) {
        this.productId = productId;
    }
}
