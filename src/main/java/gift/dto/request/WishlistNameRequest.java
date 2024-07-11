package gift.dto.request;

import jakarta.validation.constraints.NotNull;

public class WishlistNameRequest {

    @NotNull(message = "Member ID를 입력하세요")
    private Long memberId;
    @NotNull(message = "Product ID를 입력하세요")
    private Long productId;

    public WishlistNameRequest(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public Long getMemberId() {
        return memberId;
    }


    public Long getProductId() {
        return productId;
    }

}
