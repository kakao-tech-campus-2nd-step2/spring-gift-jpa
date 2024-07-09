package gift.domain.dto;

import gift.domain.entity.Product;

public record ProductResponseDto(Long id, String name, Long price, String imageUrl) {

    public static ProductResponseDto of(Product product) {
        return new ProductResponseDto(product.id(), product.name(), product.price(), product.imageUrl());
    }

    public static Product toEntity(ProductResponseDto dto) {
        return new Product(dto.id(), dto.name(), dto.price(), dto.imageUrl());
    }
}
