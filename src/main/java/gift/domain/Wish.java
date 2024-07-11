package gift;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "wish")
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private gift.Member member;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private gift.Product product;

    private Wish() {
    }

    private Wish(WishBuilder builder) {
        this.id = builder.id;
        this.member = builder.member;
        this.product = builder.product;
    }

    public Long getId() {
        return id;
    }

    public gift.Member getMember() {
        return member;
    }

    public gift.Product getProduct() {
        return product;
    }

    public static class WishBuilder {
        private Long id;
        private gift.Member member;
        private gift.Product product;

        public WishBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public WishBuilder member(gift.Member member) {
            this.member = member;
            return this;
        }

        public WishBuilder product(gift.Product product) {
            this.product = product;
            return this;
        }

        public Wish build() {
            return new Wish(this);
        }
    }
}
