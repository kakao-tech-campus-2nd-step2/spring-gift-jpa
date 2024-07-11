package gift.wish.domain;

import gift.member.domain.Member;
import gift.product.domain.Product;
import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "wishes")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private Long productId;
    @Embedded
    private ProductCount productCount;


    public Wish() {
    }

    public Wish(Long id, Long memberId, Long productId, ProductCount productCount) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.productCount = productCount;
    }

    public boolean checkNew() {
        return id == null;
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

    public ProductCount getProductCount() {
        return productCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Wish item = (Wish) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
