package gift.wishlist.dto;

import gift.product.model.Product;

public class WishResponse {

    private Long id;
    private Product product;

    public WishResponse(Long id, Product product) {
        this.id = id;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }
}
