package gift.entity;

public class Wish {
    public final Long id;
    public final Long userId;
    public final Long productId;

    public Wish(Long id, Long userId, Long productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    public Wish(Long userId, Long productId) {
        this(null, userId, productId);
    }
}
