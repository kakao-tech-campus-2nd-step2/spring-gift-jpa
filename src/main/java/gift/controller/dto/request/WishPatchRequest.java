package gift.controller.dto.request;

import jakarta.validation.constraints.Min;

public record WishPatchRequest(
        @Min(1)
        Long productId,
        @Min(0)
        int productCount
) {
}
