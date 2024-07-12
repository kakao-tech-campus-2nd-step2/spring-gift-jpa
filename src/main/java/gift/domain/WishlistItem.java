package gift.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlist")
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product", nullable = false)
    private Product product;

    public WishlistItem(Long id, Member member, Product product) {
        this.id = id;
        this.member = member;
        this.product = product;
    }

    public WishlistItem(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    public WishlistItem() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
