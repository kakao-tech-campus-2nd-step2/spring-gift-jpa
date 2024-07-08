package gift.controller.dto.request;

import jakarta.validation.constraints.Min;

public record WishInsertRequest(
        @Min(1)
        Long productId
) {
}
