package gift.wishlist.domain;

import gift.member.domain.Member;
import gift.product.domain.Product;
import jakarta.persistence.*;

@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Wishlist() {
    }

    public Wishlist(Member member, Product product) {
        this(null, member, product);
    }

    public Wishlist(Long id, Member member, Product product) {
        this.id = id;
        this.product = product;
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Member getMember() {
        return member;
    }
}
