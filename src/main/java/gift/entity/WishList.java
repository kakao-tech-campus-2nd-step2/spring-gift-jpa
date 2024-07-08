package gift.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class WishList {
    @Id
    int user_id;
    @Id
    int product_id;

    public WishList(int user_id, int product_id) {
        this.user_id = user_id;
        this.product_id = product_id;
    }

    public WishList() {
    }

    public int getUser_id() {
        return user_id;
    }

    public int getProduct_id() {
        return product_id;
    }
}
