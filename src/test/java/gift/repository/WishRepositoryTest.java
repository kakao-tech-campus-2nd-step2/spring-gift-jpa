package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.Product;
import gift.entity.User;
import gift.entity.Wish;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("유저 아이디 기반 위시리스트 반환 테스트 및 페이지 사이즈 테스트")
    void findByUserId() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Wish> page = wishRepository.findByUserId(1L, pageable);

        assertThat(page.getTotalElements()).isEqualTo(30);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.getContent().size()).isEqualTo(10);

        assertThat(page.getContent().get(0).getProduct().getName()).isEqualTo("Product 1");
    }


    @Test
    @DisplayName("유저 아이디와 위시 아이디 기반 위시 객체 반환 테스트")
    void findByUserIdAndId() {
        Wish expectedWish = wishRepository.findById(1L).orElseThrow();
        Wish actualWish = wishRepository.findByUserIdAndId(1L, 1L).orElse(null);

        assertThat(expectedWish.getId()).isEqualTo(actualWish.getId());
        assertThat(expectedWish.getProduct().getId()).isEqualTo(actualWish.getProduct().getId());
    }

    @Test
    @DisplayName("updateWishNumber JPQL 테스트")
    void updateWishNumber() {
        Wish wish = wishRepository.findById(1L).orElseThrow();
        wishRepository.updateWishNumber(1L, wish.getId(), 30);
        entityManager.flush();
        entityManager.clear();

        Wish updatedWish = wishRepository.findByUserIdAndId(1L, wish.getId()).orElse(null);

        assertThat(updatedWish.getNumber()).isEqualTo(30);
    }
}
