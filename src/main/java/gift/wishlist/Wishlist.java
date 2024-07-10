package gift.wishlist;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String memberEmail;

    @Column(nullable = false)
    private long productId;

    public Wishlist() {
    }

    public Wishlist(long productId, String memberEmail) {
        this.productId = productId;
        this.memberEmail = memberEmail;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public long getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Wishlist wishlist) {
            return id == wishlist.id
                   && productId == wishlist.productId
                   && memberEmail.equals(wishlist.memberEmail);
        }
        return false;
    }
}
