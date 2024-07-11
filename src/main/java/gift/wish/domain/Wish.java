package gift.wish.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SoftDelete;

@Entity
@SoftDelete
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long productId;

    @NotNull
    private Long userId;

    @NotNull
    private Integer amount;

    protected Wish() {
    }

    public Wish(Long id, Long productId, Long userId, Integer amount) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.amount = amount;
    }

    public Wish(Long productId, Long userId, Integer amount) {
        this(null, productId, userId, amount);
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getAmount() {
        return amount;
    }
}
