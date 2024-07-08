package gift.dto.response;

import gift.domain.Product;

public record ProductResponseDto(Long id, String name, int price, String imageUrl) {

    public static ProductResponseDto of(Long id, String name, int price, String imageUrl) {
        return new ProductResponseDto(id, name, price, imageUrl);
    }

    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

}
