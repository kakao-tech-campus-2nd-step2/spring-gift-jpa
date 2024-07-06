package gift.product.business.dto;

import gift.product.persistence.entity.Product;
import java.util.List;

public record ProductDto(
    Long id,
    String name,
    String description,
    Integer price,
    String url
) {

    public static ProductDto from(Product product) {
        return new ProductDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getUrl()
        );
    }

    public static List<ProductDto> of(List<Product> products) {
        return products.stream()
            .map(ProductDto::from)
            .toList();
    }
}
