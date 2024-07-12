package gift.domain.model.dto;

import java.util.List;

public class ProductCursorResponseDto {

    private List<ProductResponseDto> productDtos;
    private Long nextCursor;
    private boolean hasNext;

    public ProductCursorResponseDto() {
    }

    public ProductCursorResponseDto(List<ProductResponseDto> productDtos, Long nextCursor,
        boolean hasNext) {
        this.productDtos = productDtos;
        this.nextCursor = nextCursor;
        this.hasNext = hasNext;
    }

    public List<ProductResponseDto> getProductDtos() {
        return productDtos;
    }

    public Long getNextCursor() {
        return nextCursor;
    }

    public boolean isHasNext() {
        return hasNext;
    }
}
