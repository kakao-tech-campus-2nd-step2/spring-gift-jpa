package gift.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "numOfProduct", nullable = false)
    private int numOfProduct;

    protected Wishlist() {}

    public Wishlist(Member member, Product product, int numOfProduct) {
        this.member = member;
        this.product = product;
        this.numOfProduct = numOfProduct;
    }
}
