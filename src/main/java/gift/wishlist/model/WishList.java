package gift.wishlist.model;

import gift.member.model.Member;
import gift.product.model.Product;
import jakarta.persistence.*;

@Entity
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wishlist_seq")
    @SequenceGenerator(name = "wishlist_seq", sequenceName = "wishlist_sequence", allocationSize = 1)
    private Long wishlist_id;

    /** fetch = FetchType.LAZY를 사용하여 WishList가 로드될 때 Member와 Product는 즉시 로드되지 않음.
     Member나 Product에 접근할 때 데이터베이스에서 해당 데이터를 로드함. **/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    protected WishList() {
        // JPA requires a no-arg constructor
    }

    // 생성자
    public WishList(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    public Long getWishlistId() {
        return wishlist_id;
    }

    public Member getMember() {
        return member;
    }

    public Product getProduct() {
        return product;
    }
}