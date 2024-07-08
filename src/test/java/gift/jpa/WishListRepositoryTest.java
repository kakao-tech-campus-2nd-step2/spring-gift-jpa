package gift.jpa;

import gift.wishList.WishList;
import gift.wishList.WishListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WishListRepositoryTest {

    @Autowired
    WishListRepository wishListRepository;

    @Test
    void save(){
        WishList expected = new WishList(1, 1, 10);
        WishList actual = wishListRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getUserID()).isEqualTo(expected.getUserID()),
                () -> assertThat(actual.getProductID()).isEqualTo(expected.getProductID()),
                () -> assertThat(actual.getCount()).isEqualTo(expected.getCount())
        );
    }
    @Test
    void findByUserID(){
        WishList expected = new WishList(1, 1, 10);
        wishListRepository.save(expected);
        wishListRepository.save(new WishList(2, 3, 5));
        List<WishList> actual = wishListRepository.findByUserID(1L);
        assertThat(actual.size()).isEqualTo(1);

        assertAll(
                () -> assertThat(actual.get(0).getId()).isNotNull(),
                () -> assertThat(actual.get(0).getUserID()).isEqualTo(expected.getUserID()),
                () -> assertThat(actual.get(0).getProductID()).isEqualTo(expected.getProductID()),
                () -> assertThat(actual.get(0).getCount()).isEqualTo(expected.getCount())
        );
    }
    @Test
    @Transactional
    void updateCount(){
        WishList wish = wishListRepository.save(new WishList(1, 1, 10));
        wish.setCount(100);
        WishList actual = wishListRepository.findById(wish.getId()).orElseThrow();
        assertThat(actual.getCount()).isEqualTo(100);
    }
    @Test
    void deleteByID(){
        WishList wish = wishListRepository.save(new WishList(1, 1, 10));
        wishListRepository.deleteById(wish.getId());
        Optional<WishList> wishList = wishListRepository.findById(wish.getId());
        assertThat(wishList.isPresent()).isFalse();
    }
}
