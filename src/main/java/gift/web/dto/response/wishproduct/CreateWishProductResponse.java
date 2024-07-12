package gift.web.dto.response.wishproduct;

import gift.domain.WishProduct;

public class CreateWishProductResponse {

    private final Long id;

    public CreateWishProductResponse(Long id) {
        this.id = id;
    }

    public static CreateWishProductResponse fromEntity(WishProduct wishProduct) {
        return new CreateWishProductResponse(wishProduct.getId());
    }

    public Long getId() {
        return id;
    }
}
