package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.domain.WishList;
import gift.repository.wish.WishListRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishListRepositoryTest {
    @Autowired
    private WishListRepository wishListRepository;

    @Test
    void save() {
        // given
        WishList wishList = new WishList(1L, 1L, 1L, 2);
        // when
        WishList savedWish = wishListRepository.save(wishList);
        // then
        Assertions.assertAll(
            () -> assertThat(savedWish.getId()).isNotNull(),
            () -> assertThat(savedWish.getQuantity()).isEqualTo(wishList.getQuantity())
        );
    }

    @Test
    void findbyid() {
        // given
        Long id = 1L;
        WishList wish1 = new WishList(1L, 1L, 1L, 2);
        WishList wish2 = new WishList(2L, 1L, 3L, 3);
        wishListRepository.save(wish1);
        wishListRepository.save(wish2);
        // when
        Optional<WishList> findWish = wishListRepository.findById(id);
        Long findId = findWish.get().getId();
        // then
        assertThat(findId).isEqualTo(id);
    }

    

}
