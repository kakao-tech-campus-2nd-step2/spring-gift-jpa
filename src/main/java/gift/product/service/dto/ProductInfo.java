package gift.product.service.dto;

import gift.product.domain.Product;

public record ProductInfo(
        Long id,
        String name,
        Integer price,
        String imgUrl
) {
    public static ProductInfo from(Product product) {
        return new ProductInfo(product.getId(), product.getName(), product.getPrice(), product.getImgUrl());
    }
}
