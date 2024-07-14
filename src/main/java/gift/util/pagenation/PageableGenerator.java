package gift.util.pagenation;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableGenerator {
    public static Pageable generatePageable(PageInfoDTO pageInfoDTO) {
        Sort sort = Sort.by(pageInfoDTO.sort());

        if (pageInfoDTO.asc() == false) {
            sort = sort.descending();
        }

        return PageRequest.of(pageInfoDTO.page(), pageInfoDTO.size(), sort);
    }
}
