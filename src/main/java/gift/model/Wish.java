package gift.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    public Wish(long id, Member member, Product product) {
        this.id = id;
        this.member = member;
        this.product = product;
    }

    public Wish(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    public Wish() {
    }

    public long getId() {
        return id;
    }

    public Member getMemberId() {
        return member;
    }

    public Product getProductId() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wish wish = (Wish) o;
        return Objects.equals(member, wish.member) && Objects.equals(product, wish.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, product);
    }
}
