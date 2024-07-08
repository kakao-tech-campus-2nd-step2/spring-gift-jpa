package gift.model.wish;

public class Wish {

    private Long id;
    private Long memberId;
    private Long productId;
    private Long count;

    public Wish(Long id, Long memberId, Long productId, Long count) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getCount() {
        return count;
    }
}
