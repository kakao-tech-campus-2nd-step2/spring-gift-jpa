package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.model.item.Item;
import gift.model.user.User;
import gift.model.wishList.WishItem;
import java.util.List;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    private final Long testUserId = 1L;
    private final Long testItemId = 1L;
    private final WishItem testWishItem = new WishItem(1L, new User(testUserId,"test","test"), new Item(testItemId,"test",0L,"test"));

    @BeforeEach
    void setUp(){
        wishListRepository.deleteAll();
    }

    @Test
    @DisplayName("위시 아이템 추가 성공 테스트")
    void saveSuccessTest() {
        WishItem saved = wishListRepository.save(testWishItem);
        assertThat(saved).isNotNull().usingRecursiveComparison().isEqualTo(testWishItem);
    }

    @Test
    @DisplayName("위시 리스트 조회 성공 테스트")
    void findUserSuccessTest() {
        wishListRepository.save(testWishItem);
        List<WishItem> result = wishListRepository.findAllByUserId(testUserId);
        assertThat(result).extracting("userId", "ItemId")
            .contains(Tuple.tuple(testUserId, testItemId));
    }

    @Test
    @DisplayName("위시 리스트 조회 실패 테스트")
    void findUserFailTest() {
        List<WishItem> result = wishListRepository.findAllByUserId(testUserId);
        assertThat(result.size()).isEqualTo(0);
    }

}
