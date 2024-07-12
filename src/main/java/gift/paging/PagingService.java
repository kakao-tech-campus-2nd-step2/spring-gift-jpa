package gift.paging;

import static gift.controller.PagingViewController.PRODUCTS_PER_PAGE;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class PagingService {

    public PageRequest makePageRequest(int page, String sortOption) {
        return PageRequest.of(page - 1,
            PRODUCTS_PER_PAGE, Sort.by(Direction.ASC, sortOption));
    }

}
