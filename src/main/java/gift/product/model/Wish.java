package gift.product.model;

public class Wish {
    private final Long id;
    private final Long memberId;
    private final Long productId;

    public Wish(Long id, Long memberId, Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public Wish(Long memberId, Long productId) {
        this(null, memberId, productId);
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
}
