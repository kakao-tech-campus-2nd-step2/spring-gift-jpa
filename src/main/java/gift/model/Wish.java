package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "WISH_TABLE")
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WISH_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="PRODUCT_ID")
    private Product product;

    @Column(nullable = false)
    private Integer productValue;

    public Wish() {
    }

    public Wish(Member member, Product product) {
        this.member = member;
        this.product = product;
        this.productValue = 1;
    }

    public Wish(Long id, Member member, Product product, Integer productValue) {
        this.id = id;
        this.member = member;
        this.product = product;
        this.productValue = productValue;
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

    public Integer getValue() {
        return productValue;
    }

    public void setValue(Integer productValue) {
        this.productValue = productValue;
    }
}
