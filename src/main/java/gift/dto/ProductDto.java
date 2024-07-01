package gift.dto;

import gift.domain.Product;

import java.util.List;

public record ProductDto(Long id, String name, Integer price, String description, String imageUrl) {
    public static ProductDto from(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getUrl());
    }

    public static List<ProductDto> of(List<Product> products) {
        return products.stream()
                .map(ProductDto::from)
                .toList();
    }
}
