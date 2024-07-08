package gift.product.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import gift.product.exception.ProductNameLengthException;
import gift.product.exception.ProductNamePatternException;

import java.util.Objects;
import java.util.regex.Pattern;

public class ProductName {
    private static final int MAX_LENGTH = 15;
    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣 ()\\[\\]+\\-&/_]*$");

    private String value;

    public ProductName() {}

    public ProductName(String value) {
        if (Objects.isNull(value)) {
            throw new ProductNameLengthException();
        }

        value = value.trim();

        if (value.isEmpty() || value.length() > MAX_LENGTH) {
            throw new ProductNameLengthException();
        }

        if (!PATTERN.matcher(value).matches()) {
            throw new ProductNamePatternException();
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

    // JSON 직렬화를 위해 @JsonValue 사용
    @JsonValue
    public String toJson() {
        return value;
    }
}