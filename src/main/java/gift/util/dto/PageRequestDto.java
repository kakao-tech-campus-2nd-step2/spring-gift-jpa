package gift.util.dto;

import org.hibernate.validator.constraints.Range;

public record PageRequestDto(
    @Range(min = 0, message = "0 이상의 페이지 값을 입력해주세요.")
    Integer page,

    String sortBy,

    String orderBy,

    @Range(min = 1, max = 100, message = "페이지 크기를 1 이상 100 이하로 입력해주세요.")
    Integer size
) {
    public PageRequestDto {
        if (page == null) {
            page = 0;
        }
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "id";
        }
        if (orderBy == null || orderBy.isEmpty()) {
            orderBy = "asc";
        }
        if (size == null) {
            size = 10;
        }
    }
}
