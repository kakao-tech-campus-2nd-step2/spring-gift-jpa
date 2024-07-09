package gift.domain;

public class WishProduct extends BaseEntity {

    private final Member member;
    private final Product product;
    private Integer quantity;

    public static class Builder extends BaseEntity.Builder<WishProduct.Builder> {

        private Member member;
        private Product product;
        private Integer quantity;

        public Builder member(Member member) {
            this.member = member;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public WishProduct build() {
            return new WishProduct(this);
        }
    }

    private WishProduct(Builder builder) {
        super(builder);
        member = builder.member;
        product = builder.product;
        quantity = builder.quantity;
    }

    public Member getMember() {
        return member;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer updateQuantity(Integer quantity) {
        this.quantity = quantity;
        return this.quantity;
    }

    public Integer addQuantity(Integer quantity) {
        this.quantity += quantity;
        return this.quantity;
    }
}
