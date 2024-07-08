package gift.model.wish;

public class Wish {

    private Long id;
    private String userId;
    private Long productId;
    private Long count;

    public Wish(Long id, String userId, Long productId, Long count) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getCount() {
        return count;
    }
}
