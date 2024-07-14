package gift.mapper;

import gift.domain.product.Product;
import gift.web.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product) {
        return new ProductDto(product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl());
    }

    public Product toEntity(ProductDto productDto) {
        return new Product(productDto.name(),
            productDto.price(),
            productDto.imageUrl()
        );
    }
}
