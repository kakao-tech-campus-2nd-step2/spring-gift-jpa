package gift.web.dto.response.product;

import gift.domain.Product;

public class CreateProductResponse {

    private final Long id;

    public CreateProductResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static CreateProductResponse fromEntity(Product product) {
        return new CreateProductResponse(product.getId());
    }
}
