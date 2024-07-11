package gift.wishlist;

import gift.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String memberEmail;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Product product;

    public Wishlist() {
    }

    public Wishlist(Product product, String memberEmail) {
        this.product = product;
        this.memberEmail = memberEmail;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Wishlist wishlist) {
            return id == wishlist.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) this.id;
    }
}
