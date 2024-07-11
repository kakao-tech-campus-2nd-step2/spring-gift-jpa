package gift.domain.dto;

import gift.global.response.BasicResponse;
import java.util.List;
import org.springframework.http.HttpStatusCode;

public class ProductListResponseDto extends BasicResponse {

    private final List<ProductResponseDto> products;

    public ProductListResponseDto(HttpStatusCode status, List<ProductResponseDto> products) {
        super(status);
        this.products = products;
    }

    public List<ProductResponseDto> getProducts() {
        return products;
    }
}
