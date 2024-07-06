package gift.product.business.dto;

import gift.product.persistence.entity.Product;

public record ProductRegisterDto(
    String name,
    String description,
    Integer price,
    String url
) {

    public Product toProduct() {
        return new Product(name, description, price, url);
    }
}
