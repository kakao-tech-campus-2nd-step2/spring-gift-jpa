package gift.domain.wish;

import gift.domain.member.Member;
import gift.domain.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;

    public Wish(Member member, Product product, Integer quantity) {
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    protected Wish() {
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
