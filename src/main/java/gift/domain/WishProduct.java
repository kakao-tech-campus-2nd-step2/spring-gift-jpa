package gift.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "wishList")
public class WishProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    @Column(nullable = false)
    String userId;
    @Column(nullable = false)
    Long productId;
    @Column(nullable = false)
    int count;

    protected WishProduct(){

    }

    public WishProduct(String userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
        count = 1;

    }

    public String getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }
    public int getCount(){
        return count;
    }
    public void changeCount(int count){
        if (count < 0) {
            count = 0;
        }
        this.count = count;
    }
}
