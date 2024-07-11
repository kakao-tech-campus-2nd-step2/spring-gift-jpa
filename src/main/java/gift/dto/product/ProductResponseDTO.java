package gift.dto.product;

import gift.entity.Product;

public record ProductResponseDTO(
        long id,
        int price,
        String name,
        String imageUrl
) {
    public static ProductResponseDTO from(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getPrice(),
                product.getName(),
                product.getImageUrl()
        );
    }
}
