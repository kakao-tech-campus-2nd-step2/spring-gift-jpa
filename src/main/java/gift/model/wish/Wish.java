package gift.model.wish;

import gift.model.member.Member;
import gift.model.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Wish {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private Long count;

    protected Wish() {
    }

    public Wish(Long id, Member member, Product product, Long count) {
        this.id = id;
        this.member = member;
        this.product = product;
        this.count = count;
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

    public Long getCount() {
        return count;
    }
}
