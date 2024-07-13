package gift.domain.model.dto;

import gift.domain.model.enums.WishSortBy;
import jakarta.validation.constraints.Min;

public class WishSearchRequestDto {

    @Min(value = 0, message = "페이지 번호는 양수이어야 합니다.")
    private Integer page = 0;

    private WishSortBy sortBy = WishSortBy.ID_DESC;

    public Integer getPage() {
        return page;
    }

    public WishSortBy getSortBy() {
        return sortBy;
    }
}