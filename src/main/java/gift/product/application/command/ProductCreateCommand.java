package gift.product.application.command;

import gift.product.domain.Product;

public record ProductCreateCommand(
        String name,
        Integer price,
        String imageUrl
) {
    public Product toProduct () {
        return new Product(name, price, imageUrl);
    }
}
