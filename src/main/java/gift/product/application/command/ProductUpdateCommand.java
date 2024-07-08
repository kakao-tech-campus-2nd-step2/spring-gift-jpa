package gift.product.application.command;

import gift.product.domain.Product;

public record ProductUpdateCommand(
        Long productId,
        String name,
        Integer price,
        String imageUrl
) {
    public Product toProduct() {
        return new Product(productId, name, price, imageUrl);
    }
}
