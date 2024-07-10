package gift.model.dto;

import gift.model.Wish;

public class WishResponseDto {

    private Long productId;
    private Integer count;

    public WishResponseDto() {
    }

    public WishResponseDto(Long productId, Integer count) {
        this.productId = productId;
        this.count = count;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getCount() {
        return count;
    }

    public static WishResponseDto from(Wish wish) {
        return new WishResponseDto(
            wish.getProductId(),
            wish.getCount()
        );
    }
}
