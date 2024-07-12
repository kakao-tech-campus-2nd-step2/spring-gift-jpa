package gift.domain.dto.response;

import gift.domain.entity.Product;

public record ProductResponse(Long id, String name, Long price, String imageUrl) {

    public static ProductResponse of(Product product) {
        return new ProductResponse(product.id(), product.name(), product.price(), product.imageUrl());
    }

    public static Product toEntity(ProductResponse dto) {
        return new Product(dto.id(), dto.name(), dto.price(), dto.imageUrl());
    }
}
