package gift.product.service.dto;

import gift.product.domain.Product;

public record ProductParam(
        String name,
        Integer price,
        String imgUrl
) {
    public Product toEntity() {
        return new Product(name, price, imgUrl);
    }
}
