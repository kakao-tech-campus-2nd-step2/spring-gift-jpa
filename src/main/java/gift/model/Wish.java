package gift.model;

public class Wish {
    private Long id;
    private Long productId;
    private Long userId;

    public Wish() {
    }

    public Wish(Long productId, Long userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getUserId() {
        return userId;
    }
}
