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
    
}
