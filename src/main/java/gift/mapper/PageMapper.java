package gift.mapper;

import gift.util.page.PageResult;
import org.springframework.data.domain.Page;

public class PageMapper {

    private PageMapper() {
    }

    public static <T> PageResult<T> toPageResult(Page<T> page) {
        return new PageResult<>(
            page.getContent(),
            page.getSize(),
            page.getNumber(),
            page.getTotalPages(),
            page.hasNext(),
            page.getTotalElements()
        );
    }
}
