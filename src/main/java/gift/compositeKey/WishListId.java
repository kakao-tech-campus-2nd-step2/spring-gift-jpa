package gift.compositeKey;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class WishListId implements Serializable {

    int user_id;
    int product_id;

    // 기본 생성자
    public WishListId() {
    }

    // 매개 변수가 있는 생성자
    public WishListId(int user_id, int product_id) {
        this.user_id = user_id;
        this.product_id = product_id;
    }

    // hashCode와 equals 메서드
    @Override
    public int hashCode() {
        return Objects.hash(user_id, product_id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WishListId that = (WishListId) obj;
        return user_id == that.user_id && product_id == that.product_id;
    }

    // getters and setters

    public int getUser_id() {
        return user_id;
    }

    public int getProduct_id() {
        return product_id;
    }
}