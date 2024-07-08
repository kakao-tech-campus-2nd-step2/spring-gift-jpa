package gift.product.application.dto.response;

import gift.product.domain.Product;

public record ProductResponse(
        Long id,
        String name,
        Integer price,
        String imgUrl
) {
    public static ProductResponse fromModel(final Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImgUrl());
    }
}
