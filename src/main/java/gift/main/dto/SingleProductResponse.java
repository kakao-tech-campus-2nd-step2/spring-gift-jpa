package gift.main.dto;

import gift.main.entity.Product;

public class SingleProductResponse extends Response {
    private Product product;

    public Product getProduct() {
        return product;
    }

    public SingleProductResponse(int code, String message, Product product) {
        super(code, message);
        this.product = product;
    }
}
