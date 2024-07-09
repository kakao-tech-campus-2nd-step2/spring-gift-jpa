package gift.controller.product.dto;

import gift.model.product.Product;

public class ProductResponse {

    public record ProductInfoResponse(
        Long productId,
        String name,
        Integer price,
        String imageUrl
    ) {

        public static ProductInfoResponse from(Product product) {
            return new ProductInfoResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
            );
        }
    }
}
