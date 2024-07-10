package gift.dto;

public class WishRequest {
    private Long productId;

    public WishRequest() {
    }


    private WishRequest(Builder builder) {
        this.productId = builder.productId;
    }


    public Long getProductId() {
        return productId;
    }


    public static class Builder {
        private Long productId;

        public Builder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public WishRequest build() {
            return new WishRequest(this);
        }
    }


    public static Builder builder() {
        return new Builder();
    }
}
