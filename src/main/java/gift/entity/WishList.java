package gift.entity;

import gift.compositeKey.WishListId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
public class WishList {

    @EmbeddedId
    private WishListId id;

    // getters and setters

    public WishList(WishListId id) {
        this.id = id;
    }

    public WishList() {
    }

    public WishListId getId() {
        return id;
    }
}
