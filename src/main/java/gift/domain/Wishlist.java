package gift.domain;

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

    @Column(name = "num", nullable = false)
    private int num;

    protected Wishlist() {}

    public Wishlist(Member member, Product product, int num) {
        this.member = member;
        this.product = product;
        this.num = num;
    }
}
