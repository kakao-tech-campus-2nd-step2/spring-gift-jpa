package gift.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "wish")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int count;

    public Wish() {
        this.count = 1; // 기본값 1로 초기화
    }

    public Wish(Member member, Product product) {
        this.member = member;
        this.product = product;
        this.count = 1; // 기본값 1로 초기화
    }

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void incrementCount() {
        this.count++;
    }

    public void decrementCount() {
        if (this.count > 1) {
            this.count--;
        }
    }

    @Override
    public String toString() {
        return "Wish{" +
                "id=" + id +
                ", memberId=" + (member != null ? member.getId() : null) +
                ", productId=" + (product != null ? product.getId() : null) +
                ", count=" + count +
                '}';
    }
}
