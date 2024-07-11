package gift;

public class Wish {
    private final Long id;
    private final Long memberId;
    private final Long productId;

    public Wish(Long id, Long memberId, Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
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

    public static class WishBuilder {
        private Long id;
        private Long memberId;
        private Long productId;

        public WishBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public WishBuilder memberId(Long memberId) {
            this.memberId = memberId;
            return this;
        }

        public WishBuilder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public Wish build() {
            return new Wish(id, memberId, productId);
        }
    }
}
