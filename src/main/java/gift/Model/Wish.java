package gift.Model;

import jakarta.persistence.*;

@Entity
public class Wish {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (nullable = false)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Product product;
    @Column(nullable = false)
    int count;

    protected Wish(){}

    public Wish(Long id,Member member, Product product, int count) {
        this.member = member;
        this.id = id;
        this.product = product;
        this.count = count;
    }

    public Wish(Member member, Product product, int count) {
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

    public int getCount() {
        return count;
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

    public void setCount(int count) {
        this.count = count;
    }
}
