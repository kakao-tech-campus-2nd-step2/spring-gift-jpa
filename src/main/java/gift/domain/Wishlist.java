package gift.domain;

import gift.domain.member.Member;
import jakarta.persistence.*;

@Entity
public class Wishlist {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne
    private Member member;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne
    private Product product;

    public Wishlist() {
    }

    public Wishlist(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

}
