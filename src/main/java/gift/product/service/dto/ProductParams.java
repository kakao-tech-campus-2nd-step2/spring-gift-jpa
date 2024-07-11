package gift.product.service.dto;

import gift.product.domain.Product;

public record ProductParams(
        String name,
        Integer price,
        String imgUrl
) {
    public Product toEntity() {
        return new Product(name, price, imgUrl);
    }

    public Product toEntity(Long id) {
        return new Product(id, name, price, imgUrl);
    }
}
