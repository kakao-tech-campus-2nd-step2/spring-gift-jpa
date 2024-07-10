package gift.domain;

import jakarta.persistence.*;

@Entity
@Table(name="wishes")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
