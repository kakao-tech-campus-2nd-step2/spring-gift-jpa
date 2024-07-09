package gift.wishlist;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record Wishlist(
    @Id
    long productId,

    @Column(nullable = false)
    String memberEmail) {

}
