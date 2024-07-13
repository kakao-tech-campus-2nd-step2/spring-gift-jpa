package gift.model.dto;

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


    public boolean isCountZero() {
        return count == 0;
    }
}
