package gift.repository;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WishRepositoryTest {
    @Autowired
    private WishRepository wishes;
    @Autowired
    private EntityManager entityManager;

    private Product expectedProduct;
    private Member expectedMember;
    private int expectedQuantity;
    private Wish expected;

    @BeforeEach
    void setupWish(){
        expectedProduct = new Product("아메리카노",2000,"http://example.com/americano");
        entityManager.persist(expectedProduct);

        expectedMember = new Member("a@a.com","1234");
        entityManager.persist(expectedMember);

        entityManager.flush();

        expectedQuantity = 10;
        expected = new Wish(expectedMember,expectedProduct,expectedQuantity);
    }

    @Test
    @DisplayName("위시 저장 테스트")
    void save() {
        // when
        Wish actual = wishes.save(expected);

        // then
        assertAll(
                ()->assertThat(actual.getId()).isNotNull(),
                ()->assertThat(actual.getProduct()).isEqualTo(expected.getProduct()),
                ()->assertThat(actual.getMember()).isEqualTo(expected.getMember()),
                ()->assertThat(actual.getQuantity()).isEqualTo(expected.getQuantity())
        );
    }

    @Test
    @DisplayName("위시 멤버 아이디로 위시 조회 테스트")
    void findByMemberId() {
        // given
        Wish savedWish = wishes.save(expected);
        entityManager.flush();
        entityManager.clear();

        // when
        Wish findWish = wishes.findById(savedWish.getId()).get();

        // then
        assertAll(
                () -> assertThat(findWish.getId()).isNotNull(),
                () -> assertThat(findWish.getMember().getEmail()).isEqualTo(savedWish.getMember().getEmail()),
                () -> assertThat(findWish.getProduct().getName()).isEqualTo(savedWish.getProduct().getName()),
                () -> assertThat(findWish.getQuantity()).isEqualTo(savedWish.getQuantity())
        );
    }

    @Test
    @DisplayName("위시 아이디와 멤버 아이디로 위시 조회 테스트")
    void findByIdAndMemberId() {
        // given
        Wish savedWish = wishes.save(expected);
        entityManager.flush();
        entityManager.clear();

        // when
        Wish findWish = wishes.findByIdAndMemberId(savedWish.getId(),savedWish.getMember().getId()).get();

        // then
        assertAll(
                () -> assertThat(findWish.getId()).isNotNull(),
                () -> assertThat(findWish.getMember().getEmail()).isEqualTo(savedWish.getMember().getEmail()),
                () -> assertThat(findWish.getProduct().getName()).isEqualTo(savedWish.getProduct().getName()),
                () -> assertThat(findWish.getQuantity()).isEqualTo(savedWish.getQuantity())
        );
    }

    @Test
    @DisplayName("위시 아이디로 위시 삭제 테스트")
    void deleteById() {
        // given
        Wish savedWish = wishes.save(expected);

        // when
        wishes.deleteById(savedWish.getId());

        // then
        List<Wish> findWishes = wishes.findAll();
        assertAll(
                () -> assertThat(findWishes.size()).isEqualTo(0)
        );
    }

    @Test
    @DisplayName("위시 페이지 조회 테스트")
    void testFindByMemberId() {
        // given
        wishes.save(new Wish(expectedMember,expectedProduct,1));
        wishes.save(new Wish(expectedMember,expectedProduct,2));
        wishes.save(new Wish(expectedMember,expectedProduct,3));

        // when
        Pageable pageable = PageRequest.of(0,2, Sort.by("id").descending());
        Page<Wish> page = wishes.findByMemberId(expectedMember.getId(), pageable);

        // then
        assertAll(
                () -> assertThat(page).isNotNull(),
                () -> assertThat(page.getContent().size()).isEqualTo(2),
                () -> assertThat(page.getTotalElements()).isEqualTo(3),
                () -> assertThat(page.getTotalPages()).isEqualTo(2)
        );
    }
}