package gift.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="wish")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    // 테스트 시 유용
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wish wish = (Wish) o;
        return Objects.equals(id, wish.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    protected Wish () {
    }

    public Wish(Long id, Member member, Product product, int quantity) {
        this.id = id;
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    public Wish(Member member, Product product, int quantity) {
        this.member = member;
        this.product = product;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getWishes().remove(this);
        }

        this.member = member;

        if (member != null) {
            member.getWishes().add(this);
        }
    }

    public void setProduct(Product product) {
        if (this.product != null) {
            this.product.getWishes().remove(this);
        }

        this.product = product;

        if (product != null) {
            product.getWishes().add(this);
        }
    }
}