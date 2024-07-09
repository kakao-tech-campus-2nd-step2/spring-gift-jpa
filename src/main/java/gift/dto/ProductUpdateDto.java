package gift.dto;

import gift.vo.Product;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record ProductUpdateDto (
        Long id,
        @NotEmpty(message = "상품명을 입력해주세요.")
        String name,
        @Positive(message = "가격을 입력해 주세요(0보다 커야 합니다.)")
        int price,
        String imageUrl
) {
    public Product toProduct() {
        return new Product(id, name, price, imageUrl);
    }
}
