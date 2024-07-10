package gift.doamin.wishlist.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.doamin.wishlist.entity.WishList;
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
        WishList wishList = new WishList(1L, 1L, 3);

        WishList savedWishList = jpaWishListRepository.save(wishList);

        assertThat(savedWishList.getId()).isNotNull();
    }

    @Test
    void existsByUserIdAndProductId() {
        WishList wishList = new WishList(1L, 1L, 3);
        jpaWishListRepository.save(wishList);

        assertAll(
            () -> assertThat(jpaWishListRepository.existsByUserIdAndProductId(1L, 1L)).isTrue(),
            () -> assertThat(jpaWishListRepository.existsByUserIdAndProductId(1L, 2L)).isFalse()
        );
    }

    @Test
    void findAllByUserId() {
        WishList wishList1 = new WishList(1L, 1L, 1);
        jpaWishListRepository.save(wishList1);
        WishList wishList2 = new WishList(1L, 2L, 2);
        jpaWishListRepository.save(wishList2);

        List<WishList> wishLists = jpaWishListRepository.findAllByUserId(1L);

        assertThat(wishLists.size()).isEqualTo(2);
    }

    @Test
    void findByUserIdAndProductId() {
        WishList wishList = new WishList(1L, 1L, 3);
        WishList savedWishList = jpaWishListRepository.save(wishList);

        var foundWishList = jpaWishListRepository.findByUserIdAndProductId(1L,1L);

        assertThat(foundWishList.get()).isEqualTo(savedWishList);
    }

    @Test
    void deleteById() {
        WishList wishList = new WishList(1L, 1L, 3);
        WishList savedWishList = jpaWishListRepository.save(wishList);

        jpaWishListRepository.deleteById(savedWishList.getId());
        Optional<WishList> foundWishList = jpaWishListRepository.findById(savedWishList.getId());

        assertThat(foundWishList.isEmpty()).isTrue();

    }

}