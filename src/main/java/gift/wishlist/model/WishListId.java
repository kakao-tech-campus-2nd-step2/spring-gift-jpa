package gift.wishlist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

// Wish list는 pk가 복합 키이므로 class로 만들어주었습니다.
@Embeddable
public class WishListId implements Serializable {
    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private long productId;

    protected WishListId() {}
}
