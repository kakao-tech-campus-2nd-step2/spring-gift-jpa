package gift.product.presentation.dto;

import gift.product.business.dto.ProductPagingDto;
import java.util.List;

public record ResponsePagingProductDto(
    boolean hasNext,
    List<ResponseProductDto> productList
) {

    public static ResponsePagingProductDto from(ProductPagingDto productPagingDto) {
        return new ResponsePagingProductDto(
            productPagingDto.hasNext(),
            ResponseProductDto.of(productPagingDto.productList())
        );
    }
}
