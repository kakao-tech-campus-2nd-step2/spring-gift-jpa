package gift.dto.wish;

import jakarta.validation.constraints.NotNull;

public record WishCreateRequest(
    @NotNull(message = "상품 ID는 필수 입력 항목입니다.")
    Long productId
) { }
