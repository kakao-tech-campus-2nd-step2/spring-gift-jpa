package gift.dto.response;

import gift.dto.Product;

public class WishedProductResponse {
    Product product;
    int amount;

    public WishedProductResponse(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }
}
