package gift.dto;

public class WishListDTO {

    private Long memberId;
    private Long productId;
    private Integer productValue;

    public WishListDTO() {
    }

    public WishListDTO(Long memberId, Long productId, Integer productValue) {
        this.memberId = memberId;
        this.productId = productId;
        this.productValue = productValue;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getProductValue() {
        return productValue;
    }

    public void setProductValue(Integer productValue) {
        this.productValue = productValue;
    }
}
