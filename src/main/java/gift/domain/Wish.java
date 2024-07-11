package gift.domain;

import jakarta.persistence.*;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    public Wish() {}

    public Wish(Long id, String productName, Long memberId) {
        this.id = id;
        this.productName = productName;
        this.memberId = memberId;
    }

    public Wish(String productName, Long memberId) {
        this.productName = productName;
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }


    public Long getMemberId() {
        return memberId;
    }

}
