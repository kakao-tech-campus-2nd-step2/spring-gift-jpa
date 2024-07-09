package gift.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlist")
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    public WishlistItem(Long id, Long memberId, String itemName) {
        this.id = id;
        this.memberId = memberId;
        this.itemName = itemName;
    }

    public WishlistItem(Long memberId, String itemName) {
        this.memberId = memberId;
        this.itemName = itemName;
    }

    public WishlistItem() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
