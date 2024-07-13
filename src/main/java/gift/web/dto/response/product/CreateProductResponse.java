package gift.web.dto.response.product;

public class CreateProductResponse {

    private final Long id;

    public CreateProductResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
