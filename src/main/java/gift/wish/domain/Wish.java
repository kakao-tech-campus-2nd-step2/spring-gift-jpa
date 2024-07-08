package gift.wish.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table("wishes")
public class Wish {
    @Id
    private Long id;
    private Long memberId;
    private Long productId;
    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private ProductCount productCount;

    public Wish() {}

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
}
