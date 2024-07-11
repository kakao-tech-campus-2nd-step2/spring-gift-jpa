package gift.entity;

import jakarta.persistence.*;

@Entity
public class Wish {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Wish() {}

    public Wish(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public Member getOwner() {
        return member;
    }

    public Product getProduct() {
        return product;
    }
}
