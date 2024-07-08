package gift.model;

public class WishProduct {

    private Long id;
    private Long productId;
    private Long memberId;
    private Integer count;

    public WishProduct(Long productId, Long memberId, Integer count) {
        this.productId = productId;
        this.memberId = memberId;
        this.count = count;
    }

    public WishProduct(Long id, Long productId, Long memberId, Integer count) {
        this.id = id;
        this.productId = productId;
        this.memberId = memberId;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Integer getCount() {
        return count;
    }

    public void updateCount(Integer count) {
        this.count = count;
    }
}
