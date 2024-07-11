package gift.wish.model;

import gift.member.model.Member;
import gift.product.model.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "wish")
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 활용 메서드들
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wish)) return false;
        return id != null && id.equals(((Wish) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    // Constructors, Getters, and Setters
    public Wish() {}

    public Wish(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    // Getters and setters
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
