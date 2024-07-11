package gift.dto;

import gift.domain.Product;

public class WishRequestDto {
    private Product product;
    private int quantity;

    public WishRequestDto(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
