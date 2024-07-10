package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.model.Wish;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
public class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Test
    @DisplayName("위시리스트 항목 추가 및 ID로 조회")
    public void testSaveAndFindById() {
        Wish wish = new Wish(null, 1L, 1L);
        Wish savedWish = wishRepository.save(wish);
        Optional<Wish> foundWish = wishRepository.findById(savedWish.getId());

        assertThat(foundWish).isPresent();
        assertThat(foundWish.get().getMemberId()).isEqualTo(1L);
        assertThat(foundWish.get().getProductId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("모든 위시리스트 항목 조회")
    public void testFindAllByMemberId() {
        long initialCount = wishRepository.count();

        Wish wish1 = new Wish(null, 1L, 1L);
        Wish wish2 = new Wish(null, 1L, 2L);

        wishRepository.save(wish1);
        wishRepository.save(wish2);

        assertThat(wishRepository.findAllByMemberId(1L)).hasSize((int) initialCount + 2);
    }

    @Test
    @DisplayName("위시리스트 항목 삭제")
    public void testDeleteById() {
        Wish wish = new Wish(null, 1L, 1L);
        Wish savedWish = wishRepository.save(wish);

        wishRepository.deleteById(savedWish.getId());
        Optional<Wish> foundWish = wishRepository.findById(savedWish.getId());

        assertThat(foundWish).isNotPresent();
    }

    @Test
    @DisplayName("회원 ID와 상품 ID로 위시리스트 항목 존재 여부 확인")
    public void testExistsByMemberIdAndProductId() {
        Wish wish = new Wish(null, 1L, 1L);
        wishRepository.save(wish);

        boolean exists = wishRepository.existsByMemberIdAndProductId(1L, 1L);
        assertThat(exists).isTrue();
    }
}
