package gift.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "wish")
public class UserGift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    @NotNull
    private Long userId;
    @Column(name = "gift_id")
    @NotNull
    private Long giftId;
    @Column(name ="quantity")
    @NotNull
    private int quantity;

    public UserGift() {
    }

    public UserGift(Long userId, Long giftId, int quantity) {
        this.userId = userId;
        this.giftId = giftId;
        this.quantity = quantity;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGiftId() {
        return giftId;
    }

    public int getQuantity() {
        return quantity;
    }
}
