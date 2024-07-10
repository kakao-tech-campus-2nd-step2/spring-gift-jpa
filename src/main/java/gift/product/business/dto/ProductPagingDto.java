package gift.product.business.dto;

import gift.product.persistence.entity.Product;
import java.util.List;
import org.springframework.data.domain.Page;

public record ProductPagingDto(
    boolean hasNext,
    List<ProductDto> productList
) {

    public static ProductPagingDto from(Page<Product> products) {
        return new ProductPagingDto(
            products.hasNext(),
            ProductDto.of(products.getContent())
        );
    }
}
