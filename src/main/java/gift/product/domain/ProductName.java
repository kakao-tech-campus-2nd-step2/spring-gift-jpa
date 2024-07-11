package gift.product.domain;

import gift.global.response.ErrorCode;
import gift.product.exception.ProductValidException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class ProductName {
    private static final int MAX_LENGTH = 15;
    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣 ()\\[\\]+\\-&/_]*$");

    @Column(name = "name")
    private String value;

    public ProductName() {
    }

    public ProductName(String value) {
        if (Objects.isNull(value)) {
            throw new ProductValidException(ErrorCode.PRODUCT_NAME_LENGTH_ERROR);
        }

        value = value.trim();

        if (value.isEmpty() || value.length() > MAX_LENGTH) {
            throw new ProductValidException(ErrorCode.PRODUCT_NAME_LENGTH_ERROR);
        }

        if (!PATTERN.matcher(value).matches()) {
            throw new ProductValidException(ErrorCode.PRODUCT_NAME_PATTER_ERROR);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}