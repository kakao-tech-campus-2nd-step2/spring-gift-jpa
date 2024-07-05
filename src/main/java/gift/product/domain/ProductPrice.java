package gift.product.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import gift.product.exception.ProductPriceOutOfRangeException;

public class ProductPrice {

    private Long price;

    public ProductPrice() {}

    public ProductPrice(Long price) {
        if (price < 0) {
            throw new ProductPriceOutOfRangeException();
        }
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return Long.toString(price);
    }

    // JSON 직렬화를 위해 @JsonValue 사용
    @JsonValue
    public Long toJson() {
        return price;
    }
}
