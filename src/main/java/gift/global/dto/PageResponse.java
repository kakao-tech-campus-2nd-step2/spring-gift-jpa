package gift.global.dto;

import java.util.List;

public record PageResponse<T>(
    List<T> content,
    Integer page,
    Integer size,
    Integer totalPage,
    Integer totalSize
) {

}
