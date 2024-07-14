package gift.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "wish_list")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public WishList(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    public WishList() {
    }

    public Long getId() {
        return id;
    }

    public String getMemberEmail() {
        return member != null ? member.getEmail() : null;
    }

    public void setMemberEmail(String memberEmail) {
        if (this.member == null) {
            this.member = new Member();
        }
        this.member.setEmail(memberEmail, this.member.getPassword());
    }

    public Long getProductId() {
        return product != null ? product.getId() : null;
    }

    public void setProductId(Long productId) {
        if (this.product == null) {
            this.product = new Product();
        }
        this.product.setId(productId);
    }

    public Product getProduct() {
        return product;
    }

    public Member getMember() {
        return member;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
