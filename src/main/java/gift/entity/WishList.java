package gift.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class WishList {
    @Embeddable
    public static class WishListId implements Serializable {

        @Column(name = "user_id")
        private int userId;

        @Column(name = "product_id")
        private int productId;

        // 기본 생성자
        public WishListId() {
        }

        // 매개 변수가 있는 생성자
        public WishListId(int userId, int productId) {
            this.userId = userId;
            this.productId = productId;
        }

        // hashCode와 equals 메서드
        @Override
        public int hashCode() {
            return Objects.hash(userId, productId);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            WishListId that = (WishListId) obj;
            return userId == that.userId && productId == that.productId;
        }

        // getters and setters

        public int getUser_id() {
            return userId;
        }

        public int getProduct_id() {
            return productId;
        }
    }
    @EmbeddedId
    private WishListId id;


    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // getters and setters

    public WishList(WishListId id) {
        this.id = id;
    }

    public WishList() {
    }


    public void setId(WishListId id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }


    public WishListId getId() {
        return id;
    }
}
