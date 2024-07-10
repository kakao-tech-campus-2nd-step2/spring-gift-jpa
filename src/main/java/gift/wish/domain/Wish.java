package gift.wish.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
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

    private Boolean isDeleted;

    protected Wish() {
    }

    public Wish(Long id, Long productId, Long userId, Integer amount) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.amount = amount;
        this.isDeleted = false;
    }

    public Wish(Long productId, Long userId, Integer amount) {
        this.productId = productId;
        this.userId = userId;
        this.amount = amount;
        this.isDeleted = false;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public boolean isNew() {
        return id == null;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
