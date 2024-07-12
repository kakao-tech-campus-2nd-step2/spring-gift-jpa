package gift.dto.wish;

import gift.model.Member;
import gift.model.Product;
import jakarta.validation.constraints.NotNull;

public record WishRequest(
    @NotNull(message = "회원은 필수 입력 항목입니다.")
    Member member,

    @NotNull(message = "상품은 필수 입력 항목입니다.")
    Product product
) {

}
