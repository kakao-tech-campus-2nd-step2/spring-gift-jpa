package gift.dto.response;

import gift.entity.Product;

public record ProductResponse(
        Long id,
        String name,
        Integer price,
        String imageUrl
) {
    public static ProductResponse fromProduct(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }
}
