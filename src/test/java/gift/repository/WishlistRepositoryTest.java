package gift.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import gift.model.WishlistItem;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @BeforeEach
    public void setUp() {
        // 테스트 데이터를 초기화합니다.
        wishlistRepository.deleteAll();

        WishlistItem item1 = new WishlistItem();
        item1.setUserId(1L);
        item1.setProductId(500L);
        item1.setProductName("Item 1");
        item1.setAmount(1);

        WishlistItem item2 = new WishlistItem();
        item2.setUserId(1L);
        item2.setProductId(100L);
        item2.setProductName("Item 2");
        item2.setAmount(2);

        WishlistItem item3 = new WishlistItem();
        item3.setUserId(2L);
        item3.setProductId(100L);
        item3.setProductName("Item 2");
        item3.setAmount(5);


        wishlistRepository.save(item1);
        wishlistRepository.save(item2);
        wishlistRepository.save(item3);
    }

    @Test
    public void testFindListByUserId() {
        // userId가 1인 사용자의 위시리스트를 조회합니다.
        List<WishlistItem> user1Wishlist = wishlistRepository.findListByUserId(1L);
        assertThat(user1Wishlist).hasSize(2);

        // userId가 2인 사용자의 위시리스트를 조회합니다.
        List<WishlistItem> user2Wishlist = wishlistRepository.findListByUserId(2L);
        assertThat(user2Wishlist).hasSize(1);
    }
}
