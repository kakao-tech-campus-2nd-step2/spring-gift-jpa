package gift.domain.dto.response;

import gift.domain.entity.Product;

public record ProductResponse(Long id, String name, Integer price, String imageUrl) {

    public static ProductResponse of(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }
}
