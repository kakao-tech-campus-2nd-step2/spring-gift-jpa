package gift.domain.wishlist.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.domain.product.dao.ProductDao;
import gift.domain.product.entity.Product;
import gift.domain.user.dao.UserDao;
import gift.domain.user.entity.Role;
import gift.domain.user.entity.User;
import gift.domain.wishlist.entity.WishItem;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class WishlistDaoTest {
    
    @Autowired
    private WishlistDao wishlistDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;


    private static final User user = new User(1L, "testUser", "test@test.com", "test123", Role.USER);
    private static final Product product = new Product(1L, "아이스 카페 아메리카노 T", 4500, "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg");


    @BeforeEach
    void setUp() {
        userDao.insert(user);
        productDao.insert(product);
    }

    @Test
    @DisplayName("DB 위시리스트 추가")
    void insert() {
        // given
        WishItem wishItem = new WishItem(null, user, product);

        // when
        WishItem savedItem = wishlistDao.insert(wishItem);

        // then
        assertAll(
            () -> assertThat(savedItem).isNotNull(),
            () -> assertThat(savedItem.getId()).isNotNull(),
            () -> assertThat(savedItem.getUser()).isEqualTo(user),
            () -> assertThat(savedItem.getProduct()).isEqualTo(product)
        );
    }

    @Test
    @DisplayName("DB 위시리스트 조회")
    void findAll() {
        // given
        WishItem wishItem = new WishItem(null, user, product);
        WishItem savedItem = wishlistDao.insert(wishItem);

        // when
        List<WishItem> wishItems = wishlistDao.findAll(user);

        // then
        assertAll(
            () -> assertThat(wishItems.size()).isEqualTo(1),
            () -> assertThat(wishItems.get(0).getId()).isNotNull(),
            () -> assertThat(wishItems.get(0)).usingRecursiveComparison().isEqualTo(savedItem)
        );
    }

    @Test
    @DisplayName("DB 위시리스트 삭제")
    void delete() {
        // given
        WishItem wishItem = new WishItem(null, user, product);
        WishItem savedWishItem = wishlistDao.insert(wishItem);

        // when
        Integer numberOfDeletedRows = wishlistDao.delete(savedWishItem.getId());

        // then
        assertThat(numberOfDeletedRows).isEqualTo(1);
    }
}