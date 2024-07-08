package gift.product.application;

import gift.product.domain.Product;

public record ProductResponse(
        Long id,
        String name,
        Integer price,
        String imageUrl
){
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }
}
