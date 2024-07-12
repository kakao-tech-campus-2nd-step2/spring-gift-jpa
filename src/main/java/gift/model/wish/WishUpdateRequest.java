package gift.model.wish;

import jakarta.validation.constraints.Min;

public record WishUpdateRequest(
    @Min(0) int count) {
}
