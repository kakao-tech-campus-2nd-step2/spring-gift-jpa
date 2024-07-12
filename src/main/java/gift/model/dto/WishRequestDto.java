package gift.model.dto;

import gift.model.Wish;

public class WishRequestDto {

    private Long productId;
    private Integer count;

    public WishRequestDto() {
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getCount() {
        return count;
    }

    public Wish toEntity() {
        return new Wish(
            count
        );
    }

    public boolean isCountZero() {
        return count == 0;
    }
}
