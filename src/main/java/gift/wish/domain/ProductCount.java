package gift.wish.domain;

import gift.global.response.ErrorCode;
import gift.product.exception.ProductValidException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ProductCount {
    @Column(name = "product_count")
    private Long value;

    public ProductCount() {
    }

    public ProductCount(Long value) {
        if (value < 0) {
            throw new ProductValidException(ErrorCode.PRODUCT_COUNT_OUT_OF_RANGE_ERROR);
        }
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCount that = (ProductCount) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
