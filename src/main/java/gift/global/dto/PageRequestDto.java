package gift.global.dto;

import gift.global.utility.SortingStateUtility;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record PageRequestDto(
    @Min(value = 0, message = "잘못된 접근입니다.")
    int pageNumber,

    @Min(value = SortingStateUtility.STATE_NUM_MIN, message = "잘못된 접근입니다.")
    @Max(value = SortingStateUtility.STATE_NUM_MAX, message = "잘못된 접근입니다.")
    int sortingState
) {
    public static final int PAGE_SIZE = 10;
}
