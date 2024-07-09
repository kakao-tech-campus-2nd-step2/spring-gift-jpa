package gift.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "wish")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gift_id")
    @NotNull
    private Gift gift;
    @Column(name ="quantity")
    @NotNull
    private int quantity;

    public Wish() {
    }

    public Wish(User user, Gift gift, int quantity) {
        this.user = user;
        this.gift = gift;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Gift getGift() {
        return gift;
    }

    public int getQuantity() {
        return quantity;
    }
}
