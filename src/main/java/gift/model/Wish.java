package gift.model;

import jakarta.persistence.*;

@Entity
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    private Wish(Builder builder) {
        this.id = builder.id;
        this.member = builder.member;
        this.product = builder.product;
    }

    public Wish() {

    }


    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Product getProduct() {
        return product;
    }


    public static class Builder {
        private Long id;
        private Member member;
        private Product product;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder member(Member member) {
            this.member = member;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Wish build() {
            return new Wish(this);
        }
    }


    public static Builder builder() {
        return new Builder();
    }
}