package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import gift.product.dto.LoginMember;
import gift.product.dto.WishDto;
import gift.product.model.Product;
import gift.product.model.Wish;
import gift.product.repository.ProductRepository;
import gift.product.service.WishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class WishServiceTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    WishService wishService;

    @BeforeEach
    void 위시리스트_항목_추가() {
        for (int i = 1; i <= 9; i++) {
            Product product = productRepository.save(new Product((long) i, "테스트" + i, 1000 + i, "테스트주소" + i));
            wishService.insertWish(new WishDto(product.getId()), new LoginMember(1L));
        }
    }

    @Test
    void 위시리스트_전체_조회_페이지_테스트() {
        int WISH_COUNT = 9;
        int PAGE = 1;
        int SIZE = 4;
        String SORT = "product.name";
        String DIRECTION = "desc";

        Pageable pageable = PageRequest.of(PAGE, SIZE, Sort.Direction.fromString(DIRECTION), SORT);
        Page<Wish> wishes = wishService.getWishAll(pageable);

        assertSoftly(softly -> {
            assertThat(wishes.getTotalPages()).isEqualTo(
                (int) Math.ceil((double) WISH_COUNT / SIZE));
            assertThat(wishes.getTotalElements()).isEqualTo(WISH_COUNT);
            assertThat(wishes.getSize()).isEqualTo(SIZE);
            assertThat(wishes.getContent().get(0).getProduct().getName()).isEqualTo(
                "테스트" + (WISH_COUNT - SIZE));
        });
    }
}
