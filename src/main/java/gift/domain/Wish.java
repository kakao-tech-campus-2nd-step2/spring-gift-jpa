package gift.domain;

public class Wish {
    private Long id;
    private Long memberId;
    private Long productId;

    public Wish(){}
    public Wish(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public Wish(Long id, Long memberId, Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public Long getId(){return this.id;}
    public Long getMemberId() {
        return memberId;
    }
    public Long getProductId() {
        return productId;
    }

}
