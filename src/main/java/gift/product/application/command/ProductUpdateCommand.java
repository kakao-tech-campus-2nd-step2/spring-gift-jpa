package gift.product.application.command;

import gift.product.domain.Product;

public record ProductUpdateCommand(
        Long id,
        String name,
        Integer price,
        String imageUrl
) {
    public Product toProduct() {
        return new Product(id, name, price, imageUrl);
    }
}
