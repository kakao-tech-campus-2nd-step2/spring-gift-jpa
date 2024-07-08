package gift.mapper;

import gift.dto.ProductResponseDto;
import gift.entity.Product;

public class ProductMapper {
    public static ProductResponseDto toProductResponseDTO(Product product) {
        return new ProductResponseDto(product.getId(), product.getName().getValue(), product.getPrice(), product.getImageUrl());
    }
}
