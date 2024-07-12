package gift.global.dto;

import java.util.List;
import org.springframework.data.domain.Page;

public record PageResponse<T>(
    List<T> content,
    Integer page,
    Integer size,
    Integer totalPage,
    Integer totalSize
) {

    public static <T, U> PageResponse<U> from(List<U> content, Page<T> page) {
        return new PageResponse<>(
            content,
            page.getNumber(),
            page.getSize(),
            page.getTotalPages(),
            (int) page.getTotalElements()
        );
    }
}
