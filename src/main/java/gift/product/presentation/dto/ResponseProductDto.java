package gift.product.presentation.dto;

import gift.product.business.dto.ProductDto;
import java.util.List;

public record ResponseProductDto(
    Long id,
    String name,
    Integer price,
    String description,
    String imageUrl
) {

    public static ResponseProductDto from(ProductDto productDto) {
        return new ResponseProductDto(
            productDto.id(),
            productDto.name(),
            productDto.price(),
            productDto.description(),
            productDto.url()
        );
    }

    public static List<ResponseProductDto> of(List<ProductDto> products) {
        return products.stream()
            .map(ResponseProductDto::from)
            .toList();
    }
}
