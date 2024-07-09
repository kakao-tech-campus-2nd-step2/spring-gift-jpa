package gift.dao;

import gift.wishlist.dao.WishesRepository;
import gift.wishlist.entity.Wish;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class WishesRepositoryTest {

    @Autowired
    private WishesRepository wishesRepository;

    @Test
    @DisplayName("위시 추가 및 ID 조회 테스트")
    void saveAndFindById() {
        Wish wish = new Wish(1L, 1L);
        Wish savedWish = wishesRepository.save(wish);

        Wish foundWish = wishesRepository.findById(savedWish.getId())
                .orElse(null);

        assertThat(foundWish).isNotNull();
        assertThat(foundWish.getMemberId()).isEqualTo(savedWish.getMemberId());
        assertThat(foundWish.getProductId()).isEqualTo(savedWish.getProductId());
    }

    @Test
    @DisplayName("위시 ID 조회 실패 테스트")
    void findByIdFailed() {
        Wish wish = new Wish(1L, 100L);
        Wish savedWish = wishesRepository.save(wish);

        Wish foundWish = wishesRepository.findById(123456789L)
                .orElse(null);

        assertThat(foundWish).isNull();
    }

    @Test
    @DisplayName("위시 회원 ID 조회 테스트")
    void findByMemberId() {
        Wish wish1L = new Wish(1L, 2L);
        Wish wish2L = new Wish(2L, 4L);
        Wish wish1L2 = new Wish(1L, 6L);
        wishesRepository.save(wish1L);
        wishesRepository.save(wish2L);
        wishesRepository.save(wish1L2);

        List<Wish> wishList = wishesRepository.findByMemberId(wish1L.getMemberId());

        assertThat(wishList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("위시 회원 ID 조회 실패 테스트")
    void findByMemberIdFailed() {
        Wish wish1L = new Wish(1L, 2L);
        Wish wish2L = new Wish(2L, 4L);
        Wish wish1L2 = new Wish(1L, 6L);
        wishesRepository.save(wish1L);
        wishesRepository.save(wish2L);
        wishesRepository.save(wish1L2);

        List<Wish> wishList = wishesRepository.findByMemberId(4L);

        assertThat(wishList).isEmpty();
    }

    @Test
    @DisplayName("위시 삭제 테스트")
    void deleteWish() {
        Wish wish = new Wish(1L, 1L);
        Wish savedWish = wishesRepository.save(wish);

        wishesRepository.deleteById(savedWish.getId());

        boolean exists = wishesRepository.existsById(savedWish.getId());
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("회원 ID 및 상품 ID로 위시 존재 여부 확인 테스트")
    void existsByMemberIdAndProductId() {
        Wish wish = new Wish(1L, 1L);
        wishesRepository.save(wish);
        wishesRepository.save(new Wish(2L, 3L));
        wishesRepository.save(new Wish(3L, 1L));

        boolean exists = wishesRepository.existsByMemberIdAndProductId(wish.getMemberId(), wish.getProductId());
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("회원 ID 및 상품 ID로 위시 존재 여부 실패 확인 테스트")
    void existsByMemberIdAndProductIdFailed() {
        Wish wish = new Wish(1L, 1L);
        wishesRepository.save(wish);
        wishesRepository.save(new Wish(2L, 3L));
        wishesRepository.save(new Wish(3L, 1L));

        boolean exists = wishesRepository.existsByMemberIdAndProductId(wish.getMemberId(), 3L);
        assertThat(exists).isFalse();
    }

}