package gift.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    Long memberId;

    @NotNull
    Integer amount;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "productId")
    Product product;

    public Wish(Long memberId, Integer amount, Product product) {
        this.memberId = memberId;
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

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
