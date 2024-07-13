package gift.domain.model.dto;

import gift.domain.model.enums.ProductSortBy;
import jakarta.validation.constraints.Min;

public class ProductSearchRequestDto {

    @Min(value = 0, message = "페이지 번호는 양수이어야 합니다.")
    private int page = 0;

    private ProductSortBy sortBy = ProductSortBy.ID_DESC;

    public int getPage() {
        return page;
    }

    public ProductSortBy getSortBy() {
        return sortBy;
    }
}