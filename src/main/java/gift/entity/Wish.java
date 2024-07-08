package gift.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long memberId;

    @Column
    private Long productId;

    public Wish() {
    }


    public Wish(Long memberId, Long productId) {
        this.memberId=memberId;
        this.productId=productId;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }
}
