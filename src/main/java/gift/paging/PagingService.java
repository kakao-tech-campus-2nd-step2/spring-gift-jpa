package gift.paging;

import static gift.controller.PagingViewController.PRODUCTS_PER_PAGE;
import static gift.controller.PagingViewController.WISH_PER_PAGE;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class PagingService {

    public PageRequest makeProductsPageRequest(int page, String sortOption) {
        return PageRequest.of(page - 1,
            PRODUCTS_PER_PAGE, Sort.by(Direction.ASC, sortOption));
    }

    public PageRequest makeWishPageRequest(int page, String sortOption) {
        if (sortOption.equals("id")) {
            return PageRequest.of(page - 1,
                WISH_PER_PAGE, Sort.by(Direction.ASC, sortOption));
        }
        return PageRequest.of(page - 1,
            WISH_PER_PAGE, Sort.by(Direction.ASC, "product." + sortOption));
    }

}
