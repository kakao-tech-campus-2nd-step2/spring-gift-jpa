package gift.paging;

import static org.assertj.core.api.Assertions.assertThat;

import gift.controller.PagingViewController;
import gift.model.Product;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@DataJpaTest
class PagingTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    WishRepository wishRepository;

    @Test
    void productIdDescSort() {
        Sort sort = Sort.by(Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(0, PagingViewController.PRODUCTS_PER_PAGE, sort);
        Page<Product> paging = productRepository.findPageBy(pageRequest);
        assertThat(paging.getContent().get(0).getId()).isEqualTo(paging.getTotalElements());
    }

    @Test
    void productNameAscSort() {
        Sort sort = Sort.by(Direction.ASC, "name");
        PageRequest pageRequest = PageRequest.of(0, PagingViewController.PRODUCTS_PER_PAGE, sort);
        Page<Product> paging = productRepository.findPageBy(pageRequest);

        IntStream.range(0, PagingViewController.PRODUCTS_PER_PAGE-1)
            .forEach(i -> {
                assertThat(paging.getContent().get(i).getName()).isLessThanOrEqualTo(paging.getContent().get(i+1).getName());
            });
    }

    @Test
    void productNameDescSort() {
        Sort sort = Sort.by(Direction.DESC, "name");
        PageRequest pageRequest = PageRequest.of(0, PagingViewController.PRODUCTS_PER_PAGE, sort);
        Page<Product> paging = productRepository.findPageBy(pageRequest);

        IntStream.range(0, PagingViewController.PRODUCTS_PER_PAGE-1)
            .forEach(i -> {
                assertThat(paging.getContent().get(i).getName()).isGreaterThanOrEqualTo(paging.getContent().get(i+1).getName());
            });
    }

    @Test
    void productPriceAscSort() {
        Sort sort = Sort.by(Direction.ASC, "price");
        PageRequest pageRequest = PageRequest.of(0, PagingViewController.PRODUCTS_PER_PAGE, sort);
        Page<Product> paging = productRepository.findPageBy(pageRequest);

        IntStream.range(0, PagingViewController.PRODUCTS_PER_PAGE-1)
            .forEach(i -> {
                assertThat(paging.getContent().get(i).getPrice()).isLessThanOrEqualTo(paging.getContent().get(i+1).getPrice());
            });
    }

    @Test
    void productPriceDescSort() {
        Sort sort = Sort.by(Direction.DESC, "price");
        PageRequest pageRequest = PageRequest.of(0, PagingViewController.PRODUCTS_PER_PAGE, sort);
        Page<Product> paging = productRepository.findPageBy(pageRequest);

        IntStream.range(0, PagingViewController.PRODUCTS_PER_PAGE-1)
            .forEach(i -> {
                assertThat(paging.getContent().get(i).getPrice()).isGreaterThanOrEqualTo(paging.getContent().get(i+1).getPrice());
            });
    }



}