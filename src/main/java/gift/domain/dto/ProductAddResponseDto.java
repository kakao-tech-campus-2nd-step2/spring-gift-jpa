package gift.domain.dto;

import gift.global.response.BasicResponse;
import org.springframework.http.HttpStatusCode;

public class ProductAddResponseDto extends BasicResponse {

    private final ProductResponseDto createdProduct;

    public ProductAddResponseDto(HttpStatusCode statusCode, ProductResponseDto createdProduct) {
        super(statusCode);
        this.createdProduct = createdProduct;
    }

    public ProductResponseDto getCreatedProduct() {
        return createdProduct;
    }
}
