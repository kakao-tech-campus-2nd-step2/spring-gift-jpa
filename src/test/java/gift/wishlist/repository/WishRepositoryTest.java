package gift.wishlist.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.wishlist.model.Wish;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishRepositoryTest {
    @Autowired
    private WishRepository wishRepository;

    @Test
    void testFindByMemberIdAndProductId() {
        Wish wish = new Wish(1L, 1L);
        wishRepository.save(wish);

        List<Wish> wishList = wishRepository.findByMemberIdAndProductId(1L,1L);
        assertThat(wishList).hasSize(1);
        assertThat(wishList.get(0).getMemberId()).isEqualTo(1L);
        assertThat(wishList.get(0).getProductId()).isEqualTo(1L);
    }

    @Test
    void testFindByMemberId() {
        Wish wish1 = new Wish(1L, 1L);
        Wish wish2 = new Wish(1L, 1L);
        wishRepository.save(wish1);
        wishRepository.save(wish2);

        List<Wish> wishList = wishRepository.findByMemberId(1L);
        assertThat(wishList).hasSize(2);
    }

    @Test
    void testSaveWish() {
        Wish wish = new Wish(1L, 1L);
        Wish saveWish = wishRepository.save(wish);

        assertThat(saveWish).isNotNull();
        assertThat(saveWish.getId()).isNotNull();
        assertThat(saveWish.getMemberId()).isEqualTo(1L);
        assertThat(saveWish.getProductId()).isEqualTo(1L);
    }

    @Test
    void testDeleteByMemberIdAndProductId() {
        Wish wish = new Wish(1L, 1L);
        wishRepository.save(wish);

        wishRepository.deleteByMemberIdAndProductId(1L, 1L);

        List<Wish> wishList = wishRepository.findByMemberIdAndProductId(1L, 1L);
        assertThat(wishList).isEmpty();
    }

    @Test
    void testExistsById() {
        Wish wish = new Wish(1L, 1L);
        Wish saveWish = wishRepository.save(wish);

        boolean exists = wishRepository.existsById(saveWish.getId());
        assertThat(exists).isTrue();
    }

    @Test
    void testDeleteById() {
        Wish wish = new Wish(1L, 1L);
        Wish saveWish = wishRepository.save(wish);

        wishRepository.deleteById(saveWish.getId());

        Optional<Wish> wishList = wishRepository.findById(saveWish.getId());
        assertThat(wishList).isEmpty();
    }

    @Test
    void testDeleteByProductId() {
        Wish wish = new Wish(1L, 1L);
        wishRepository.save(wish);

        wishRepository.deleteByMemberIdAndProductId(1L, 1L);

        List<Wish> wishList = wishRepository.findByMemberIdAndProductId(1L, 1L);
        assertThat(wishList).isEmpty();
    }
}