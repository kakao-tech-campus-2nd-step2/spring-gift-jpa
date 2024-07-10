package gift.repository;

import gift.entity.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    void save() {
        Wish expected = new Wish(1L, 1L, 1);
        Wish actual = wishlistRepository.save(expected);
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getMemberId()).isEqualTo(expected.getMemberId());
        assertThat(actual.getProductId()).isEqualTo(expected.getProductId());
        assertThat(actual.getProductNumber()).isEqualTo(expected.getProductNumber());
    }

    @Test
    void findByMemberIdAndProductId() {
        wishlistRepository.save(new Wish(1L, 1L, 1));
        Optional<Wish> actual = wishlistRepository.findByMemberIdAndProductId(1L, 1L);
        assertThat(actual).isPresent();
        assertThat(actual.get().getProductNumber()).isEqualTo(1);
    }

    @Test
    void findByMemberId() {
        wishlistRepository.save(new Wish(1L, 1L, 1));
        wishlistRepository.save(new Wish(1L, 2L, 2));
        List<Wish> wishes = wishlistRepository.findByMemberId(1L);
        assertThat(wishes).hasSize(2);
    }

    @Test
    void deleteById() {
        Wish wish = wishlistRepository.save(new Wish(1L, 1L, 1));
        wishlistRepository.deleteById(wish.getId());
        Optional<Wish> deletedWish = wishlistRepository.findById(wish.getId());
        assertThat(deletedWish).isNotPresent();
    }
}
