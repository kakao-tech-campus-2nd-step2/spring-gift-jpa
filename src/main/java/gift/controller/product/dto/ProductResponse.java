package gift.controller.product.dto;

import gift.model.product.Product;

public record ProductResponse(
    Long id,
    String name,
    Integer price,
    String imageUrl
) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl());
    }
}
