package gift.web.dto.response.wishproduct;

public class CreateWishProductResponse {

    private final Long id;

    public CreateWishProductResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
