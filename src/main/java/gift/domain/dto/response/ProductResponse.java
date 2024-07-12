package gift.domain.dto.response;

import gift.domain.entity.Product;

public record ProductResponse(Long id, String name, Long price, String imageUrl) {

    public static ProductResponse of(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public static Product toEntity(ProductResponse dto) {
        return new Product(dto.name, dto.price, dto.imageUrl);
    }
}
