package gift.controller.product.dto;

import gift.model.product.Product;

public class ProductResponse {

    public record Info(
        Long productId,
        String name,
        Integer price,
        String imageUrl
    ) {

        public static Info from(Product product) {
            return new Info(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
            );
        }
    }
}
