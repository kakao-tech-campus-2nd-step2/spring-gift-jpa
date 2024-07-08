package gift.mapper;

import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.entity.Product;
import gift.entity.ProductName;

public class ProductMapper {

    public static Product toProduct(ProductRequestDto productRequestDTO) {
        ProductName productName = new ProductName(productRequestDTO.name);
        return new Product(null, productName, productRequestDTO.price, productRequestDTO.imageUrl);
    }

    public static ProductResponseDto toProductResponseDTO(Product product) {
        return new ProductResponseDto(product.id, product.name.getValue(), product.price, product.imageUrl);
    }
}
