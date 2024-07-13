package gift.service.page;

import gift.exception.InvalidPageRequestException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PageService {

    public PageService() {
    }

    public void pageValidation(Pageable pageable) {
        sizeValidation(pageable.getPageSize());
        sortValidation(pageable.getSort());
    }

    private void sizeValidation(Integer size) {
        if (size < 5 || size > 20) {
            throw new InvalidPageRequestException("잘못된 페이지 크기 정보입니다.");
        }
    }

    private void sortValidation(Sort sort) {
        if (sort.isSorted()) {
            var order = sort.getOrderFor("id");
            if (order != null && order.getDirection().equals(Sort.Direction.DESC)) {
                return;
            }
        }
        throw new InvalidPageRequestException("잘못된 페이지 정렬 정보입니다.");
    }
}
