package gift.domain;

public class Wish {
    private Long id;
    private Long userId;
    private Long productId;
    private int count;

    public Wish(Long userId, Long productId, int count) {
        this.userId = userId;
        this.productId = productId;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }
}
