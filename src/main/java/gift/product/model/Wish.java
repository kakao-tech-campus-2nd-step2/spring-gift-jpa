package gift.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private final Member member;

    @Column(nullable = false)
    private final Long productId;

    protected Wish() {
        this(null, null, null);
    }

    public Wish(Long id, Member member, Long productId) {
        this.id = id;
        this.member = member;
        this.productId = productId;
    }

    public Wish(Member member, Long productId) {
        this(null, member, productId);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Long getProductId() {
        return productId;
    }
}
