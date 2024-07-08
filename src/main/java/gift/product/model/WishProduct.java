package gift.product.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class WishProduct {

    @NotNull(message = "ID 속성이 누락되었습니다.")
    private Long id;

    @NotNull(message = "상품 정보가 누락되었습니다.")
    private Long productId;

    @Positive(message = "위시리스트의 상품 갯수는 1이상이어야 합니다.")
    private int count;

    @NotNull(message = "희망하는 사람의 정보가 누락되었습니다.")
    private String memberEmail;

    public WishProduct(Long id, Long productId, int count, String memberEmail) {
        this.id = id;
        this.productId = productId;
        this.count = count;
        this.memberEmail = memberEmail;
    }

    public Long getId() {
        return id;
    }
    public Long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }
    public String getMemberEmail() {
        return memberEmail;
    }
}
