package gift.dto;

import gift.vo.Product;
import jakarta.validation.constraints.*;

public record ProductDto(
        @NotEmpty(message = "상품명을 입력해 주세요.")
        String name,
        @Positive(message = "가격을 입력해 주세요(0보다 커야 합니다.)")
        int price,
        String imageUrl
) {
    public Product toProduct() {
        return new Product(name, price, imageUrl);
    }
}
