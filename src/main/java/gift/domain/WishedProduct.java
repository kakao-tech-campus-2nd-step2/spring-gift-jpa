package gift.domain;

import gift.dto.WishedProductDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WishedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "member_email", nullable = false)
    String memberEmail;

    @Column(name = "product_id", nullable = false)
    Long productId;

    @Column(name = "amount", nullable = false)
    int amount;

    protected WishedProduct() {

    }

    public WishedProduct(String memberEmail, Long productId, int amount) {
        this.memberEmail = memberEmail;
        this.productId = productId;
        this.amount = amount;
    }

    public WishedProduct(long id, String memberEmail, Long productId, int amount) {
        this.id = id;
        this.memberEmail = memberEmail;
        this.productId = productId;
        this.amount = amount;
    }

    public WishedProductDTO toDTO() {
        return new WishedProductDTO(id, memberEmail, productId, amount);
    }
}
