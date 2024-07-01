package gift.dto;

import gift.model.Product;

public record ProductResponseDto(Long id, String name, int price, String imageUrl) {

    public ProductResponseDto(Product product) {
        this(product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl());
    }
}
