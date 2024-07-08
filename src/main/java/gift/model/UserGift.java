package gift.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wish")
public class UserGift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "gift_id", nullable = false)
    private Long giftId;
    @Column(name ="quantity", nullable = false)
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
