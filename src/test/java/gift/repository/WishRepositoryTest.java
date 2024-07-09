package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.Wish;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class WishRepositoryTest {

    @Autowired
    WishRepository wishRepository;

    @Test
    @DisplayName("유저 아이디 기반 위시리스트 반환 테스트")
    void findByUserId() {
        Long userId = 1L;
        Wish wish1 = new Wish(userId, 1L, "Product 1", 10);
        Wish wish2 = new Wish(userId, 2L, "Product 2", 15);
        wishRepository.save(wish1);
        wishRepository.save(wish2);
        List<Wish> wishes = wishRepository.findByUserId(userId);

        assertThat(wishes).hasSize(2);
        assertThat(wishes.getFirst().getProductName()).isEqualTo(wish1.getProductName());
    }

    @Test
    @DisplayName("유저 아이디와 위시 아이디 기반 위시 객체 반환 테스트")
    void findByUserIdAndId() {
        Long userId = 1L;
        Wish wish = new Wish(userId, 1L, "Product", 10);
        Wish actual = wishRepository.save(wish);
        Wish expected = wishRepository.findByUserIdAndId(userId, actual.getId());

        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getProductId()).isEqualTo(expected.getProductId());
    }

    @Test
    @DisplayName("위시 리스트 특정 객체 수량 변경 테스트")
    void updateWishNumber() {
        Long userId = 1L;
        Wish wish = new Wish(userId, 1L, "Product to update", 10);
        Wish savedWish = wishRepository.save(wish);
        wishRepository.updateWishNumber(userId, savedWish.getId(), 30);
        Wish updatedWish = wishRepository.findByUserIdAndId(userId, savedWish.getId());

        assertThat(updatedWish).isNotNull();
        assertThat(updatedWish.getNumber()).isEqualTo(30);
    }
}
