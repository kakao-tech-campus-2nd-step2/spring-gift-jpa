package gift.doamin.wishlist.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.doamin.wishlist.entity.Wish;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class JpaWishListRepositoryTest {

    @Autowired
    JpaWishListRepository jpaWishListRepository;

    @Test
    void save() {
        Wish wish = new Wish(1L, 1L, 3);

        Wish savedWish = jpaWishListRepository.save(wish);

        assertThat(savedWish.getId()).isNotNull();
    }

    @Test
    void existsByUserIdAndProductId() {
        Wish wish = new Wish(1L, 1L, 3);
        jpaWishListRepository.save(wish);

        assertAll(
            () -> assertThat(jpaWishListRepository.existsByUserIdAndProductId(1L, 1L)).isTrue(),
            () -> assertThat(jpaWishListRepository.existsByUserIdAndProductId(1L, 2L)).isFalse()
        );
    }

    @Test
    void findAllByUserId() {
        Wish wish1 = new Wish(1L, 1L, 1);
        jpaWishListRepository.save(wish1);
        Wish wish2 = new Wish(1L, 2L, 2);
        jpaWishListRepository.save(wish2);

        List<Wish> wishes = jpaWishListRepository.findAllByUserId(1L);

        assertThat(wishes.size()).isEqualTo(2);
    }

    @Test
    void findByUserIdAndProductId() {
        Wish wish = new Wish(1L, 1L, 3);
        Wish savedWish = jpaWishListRepository.save(wish);

        var foundWishList = jpaWishListRepository.findByUserIdAndProductId(1L,1L);

        assertThat(foundWishList.get()).isEqualTo(savedWish);
    }

    @Test
    void deleteById() {
        Wish wish = new Wish(1L, 1L, 3);
        Wish savedWish = jpaWishListRepository.save(wish);

        jpaWishListRepository.deleteById(savedWish.getId());
        Optional<Wish> foundWishList = jpaWishListRepository.findById(savedWish.getId());

        assertThat(foundWishList.isEmpty()).isTrue();

    }

}