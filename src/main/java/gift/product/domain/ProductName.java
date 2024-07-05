package gift.product.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import gift.product.exception.ProductNameLengthException;
import gift.product.exception.ProductNamePatternException;

import java.util.Objects;
import java.util.regex.Pattern;

public class ProductName {
    private static final int MAX_LENGTH = 15;
    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣 ()\\[\\]+\\-&/_]*$");

    private String name;

    public ProductName() {}

    public ProductName(String name) {
        if (Objects.isNull(name)) {
            throw new ProductNameLengthException();
        }

        name = name.trim();

        if (name.isEmpty() || name.length() > MAX_LENGTH) {
            throw new ProductNameLengthException();
        }

        if (!PATTERN.matcher(name).matches()) {
            throw new ProductNamePatternException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    // JSON 직렬화를 위해 @JsonValue 사용
    @JsonValue
    public String toJson() {
        return name;
    }
}