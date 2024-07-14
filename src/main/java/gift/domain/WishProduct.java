package gift.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "wishList")
public class WishProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_wish_product_id_ref_product_id"),
            nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(
            name = "product_id",
            foreignKey = @ForeignKey(name = "fk_wish_user_id_ref_user_id"),
            nullable = false)
    private Product product;
    @Column(nullable = false)
    int count;


    protected WishProduct(){

    }

    public WishProduct(User user, Product product) {
        this.user = user;
        this.product = product;
        count = 1;

    }

    public Long getId(){
        return id;
    }
    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
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
