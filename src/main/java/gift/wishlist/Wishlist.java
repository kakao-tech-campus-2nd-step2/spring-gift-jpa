package gift.wishlist;

import gift.member.Member;
import gift.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;

    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;

    public Wishlist() {
    }

    public Wishlist(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    public Member getMember() {
        return member;
    }

    public Product getProduct() {
        return product;
    }

    //    @Column(name = "member_id", nullable = false)
//    private Long memberId;
//
//    @Column(name = "product_id", nullable = false)
//    private Long productId;
//
//    protected Wishlist() {}
//
//    public Wishlist(Long memberId, Long productId) {
//        this.memberId = memberId;
//        this.productId = productId;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public Long getMemberId() {
//        return memberId;
//    }
//
//    public Long getProductId() {
//        return productId;
//    }
}
