package gift.wishlist.dto;

import gift.product.model.Product;

public class WishRequest {

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
