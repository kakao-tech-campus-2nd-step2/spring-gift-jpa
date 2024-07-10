package gift.domain.wish;

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

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long count;

    public Wish() {

    }

    public Wish(Long id, String email, Long productId, Long count) {
        this.id = id;
        this.email = email;
        this.productId = productId;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getCount() {
        return count;
    }


}
