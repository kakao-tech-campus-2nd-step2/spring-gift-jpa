package gift.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(indexes = @Index(name = "idx_member_id", columnList = "member_id"))
public class Wish extends BasicEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private int productCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    protected Wish() {}

    public Wish(Long id, Member member, int productCount, Product product, LocalDateTime createdAt, LocalDateTime updateAt) {
        super(id, createdAt, updateAt);
        this.member = member;
        this.productCount = productCount;
        this.product = product;
    }

    public Wish(Member member, int productCount, Product product) {
        super();
        this.member = member;
        this.productCount = productCount;
        this.product = product;
    }

    public void updateWish(Member member, int productCount, Product product) {
        this.member = member;
        this.productCount = productCount;
        this.product = product;
    }

    public boolean isOwner(Long memberId) {
        return member.getId().equals(memberId);
    }

    public boolean containsProduct(Long productId) {
        return product.getId().equals(productId);
    }

    public Member getMember() {
        return member;
    }

    public int getProductCount() {
        return productCount;
    }

    public Product getProduct() {
        return product;
    }
}
