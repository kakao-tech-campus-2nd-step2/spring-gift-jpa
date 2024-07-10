package gift.jpa;

import gift.wishList.WishList;
import gift.wishList.WishListRepository;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("위시리스트 저장 테스트")
    void save() {
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
    @DisplayName("사용자 ID로 위시리스트 조회 테스트")
    void findByUserID() {
        WishList expected = new WishList(1, 1, 10);
        wishListRepository.save(expected);
        wishListRepository.save(new WishList(2, 3, 5));
        List<WishList> actual = wishListRepository.findByUserID(1L);
        assertThat(actual.size()).isEqualTo(1);
        WishList actualWish = actual.get(0);
        assertAll(
                () -> assertThat(actualWish.getId()).isNotNull(),
                () -> assertThat(actualWish.getUserID()).isEqualTo(expected.getUserID()),
                () -> assertThat(actualWish.getProductID()).isEqualTo(expected.getProductID()),
                () -> assertThat(actualWish.getCount()).isEqualTo(expected.getCount())
        );
    }

    @Test
    @DisplayName("위시리스트의 상품 수량 변경 테스트")
    void updateCount() {
        WishList wish = wishListRepository.save(new WishList(1, 1, 10));
        wish.setCount(100);
        WishList actual = wishListRepository.findById(wish.getId()).orElseThrow();
        assertThat(actual.getCount()).isEqualTo(100);
    }

    @Test
    @DisplayName("ID로 위시리스트 삭제 테스트")
    void deleteByID() {
        WishList wish = wishListRepository.save(new WishList(1, 1, 10));
        wishListRepository.deleteById(wish.getId());
        Optional<WishList> wishList = wishListRepository.findById(wish.getId());
        assertThat(wishList.isPresent()).isFalse();
    }
}
