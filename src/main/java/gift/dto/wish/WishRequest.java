package gift.dto.wish;

import jakarta.validation.constraints.NotNull;
import gift.model.Member;
import gift.model.Product;

public record WishRequest(
    @NotNull(message = "회원은 필수 입력 항목입니다.")
    Member member,

    @NotNull(message = "상품은 필수 입력 항목입니다.")
    Product product
) {
}
