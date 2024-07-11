package gift.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "memberId", foreignKey = @ForeignKey(name = "fk_wish_member_id_ref_member_id"))
    private Member member;

    @NotNull
    private Integer amount;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "productId", foreignKey = @ForeignKey(name = "fk_wish_product_id_ref_product_id"))
    private Product product;

    public Wish(Member member, Integer amount, Product product) {
        this.member = member;
        this.amount = amount;
        this.product = product;
    }

    public Wish() {
    }

    public Long getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public Product getProduct() {
        return product;
    }

    public Member getMember() {
        return member;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
