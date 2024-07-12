package gift.product.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@NotNull(message = "ID 속성이 누락되었습니다.")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)
    //@NotNull(message = "희망하는 사람의 정보가 누락되었습니다.")
    private Member member;

    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    //@NotNull(message = "희망하는 상품의 정보가 누락되었습니다.")
    private Product product;

    public Wish() {

    }

    public Wish(Member member, Product product) {
        this.member = member;
        this.product = product;
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
}
