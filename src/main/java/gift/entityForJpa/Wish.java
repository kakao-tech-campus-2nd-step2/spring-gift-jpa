package gift.entityForJpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Integer quantity;

    public Wish() { }

    public Wish(Member member, Item item, Integer quantity) {
        this.member = member;
        this.item = item;
        this.quantity = quantity;
    }

    public Long getId() { return id; }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getQuantity() { return quantity; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public void incrementQuantity() { this.quantity++; }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Wish wish = (Wish) o;
        return Objects.equals(id, wish.id) && Objects.equals(member, wish.member)
                && Objects.equals(item, wish.item) && Objects.equals(quantity,
                wish.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, member, item, quantity);
    }

    @Override
    public String toString() {
        return "Wish{" +
                "id=" + id +
                ", item=" + item +
                ", quantity=" + quantity +
                '}';
    }
}
