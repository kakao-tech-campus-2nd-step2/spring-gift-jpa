package gift.controller.product;

import gift.domain.Product;

public record ProductResponse(Long id, String name, Long price, String imageUrl) {
    public static ProductResponse of(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }
}
