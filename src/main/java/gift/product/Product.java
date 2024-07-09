package gift.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.validator.constraints.Length;

@Entity
public record Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id,

    @Column(nullable = false)
    @Length(min = 1, max = 15)
    String name,

    @Column(nullable = false)
    int price,

    @Column(nullable = false)
    String imageUrl) {

}
