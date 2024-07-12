package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.domain.Wish;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaWishRepositoryTest {
    //TODO: id잘못됐을 때 에러
    @Autowired
    private JpaWishRepository jpaWishRepository;
    private Wish wish;

    private Long insertWish(Wish wish) {
        return jpaWishRepository.save(wish).getId();
    }

    @BeforeEach
    void setWish() {
        wish = new Wish(1L, 1L, 10);
    }

    @Test
    void 위시_추가() {
        //given
        //when
        Long insertWishId = insertWish(wish);
        Wish findWish = jpaWishRepository.findById(insertWishId).get();
        //then
        assertAll(
            () -> assertThat(findWish).isEqualTo(wish)
        );
    }

    @Test
    void 위시_조회() {
        //given
        Long insertWishId = insertWish(wish);
        //when
        Wish findWish = jpaWishRepository.findById(insertWishId).get();
        //then
        assertAll(
            () -> assertThat(findWish.getId()).isNotNull(),
            () -> assertThat(findWish.getId()).isEqualTo(insertWishId),
            () -> assertThat(findWish.getUserId()).isEqualTo(1L),
            () -> assertThat(findWish.getProductId()).isEqualTo(1L),
            () -> assertThat(findWish.getCount()).isEqualTo(10)
        );
    }

    @Test
    void 위시리스트_조회() {
        //given
        // userId = 1L
        Wish wish1 = new Wish(1L, 1L, 10);
        Wish wish2 = new Wish(1L, 2L, 2);
        Wish wish3 = new Wish(1L, 3L, 3);
        Wish wish4 = new Wish(1L, 4L, 4);

        insertWish(wish1);
        insertWish(wish2);
        insertWish(wish3);
        insertWish(wish4);
        // userId = 2L
        Wish wish5 = new Wish(2L, 4L, 4);
        insertWish(wish5);
        //when
        List<Wish> wishList1 = jpaWishRepository.findAllByUserId(1L);
        List<Wish> wishList2 = jpaWishRepository.findAllByUserId(2L);
        //then
        assertAll(
            () -> assertThat(wishList1.size()).isEqualTo(4),
            () -> assertThat(wishList2.size()).isEqualTo(1)
        );
    }

    @Test
    void 위시_삭제() {
        //given
        Long insertWishId = insertWish(wish);
        //when
        Wish findWish = jpaWishRepository.findById(insertWishId).get();
        jpaWishRepository.delete(findWish);
        //then
        List<Wish> wishList = jpaWishRepository.findAllByUserId(1L);
        assertAll(
            () -> assertThat(wishList.size()).isEqualTo(0)
        );
    }

    @Test
    void 상품_삭제() {
        //given
        Long insertWishId = insertWish(wish);
        //when
        Wish findWish = jpaWishRepository.findById(insertWishId).get();
        jpaWishRepository.delete(findWish);
        //then
        List<Wish> productList = jpaWishRepository.findAll();
        assertAll(
            () -> assertThat(productList.size()).isEqualTo(0)
        );
    }
}