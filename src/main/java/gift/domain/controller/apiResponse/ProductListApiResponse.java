package gift.domain.controller.apiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import gift.domain.dto.response.ProductResponse;
import gift.global.apiResponse.BasicApiResponse;
import java.util.List;
import org.springframework.http.HttpStatusCode;

public class ProductListApiResponse extends BasicApiResponse {

    private final List<ProductResponse> products;

    public ProductListApiResponse(
        @JsonProperty(value = "status", required = true) HttpStatusCode status,
        @JsonProperty(value = "products", required = true) List<ProductResponse> products
    ) {
        super(status);
        this.products = products;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
