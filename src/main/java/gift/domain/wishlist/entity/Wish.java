package gift.domain.wishlist.entity;

import gift.domain.member.entity.Member;
import gift.domain.product.entity.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "wish")
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    protected Wish() {
    }

    public Wish(Member member, Product product) {
        this(null, member, product);
    }

    public Wish(Long id, Member member, Product product) {
        this.id = id;
        this.member = member;
        this.product = product;

        member.getWishList().add(this);
        product.getWishList().add(this);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Product getProduct() {
        return product;
    }

    public void updateMember(Member member) {
        // 기존 memberEntity에서  wishEntity 삭제
        this.member.getWishList().remove(this);
        this.member = member;
        member.getWishList().add(this);
    }

    public void updateProduct(Product product) {
        // 기존 productEntity에서 wishEntity 삭제
        this.product.getWishList().remove(this);
        this.product = product;
        product.getWishList().add(this);
    }
}
