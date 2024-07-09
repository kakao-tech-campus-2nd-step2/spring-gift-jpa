package gift.domain.wishlist.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.domain.product.dao.ProductJpaRepository;
import gift.domain.product.entity.Product;
import gift.domain.user.dao.UserJpaRepository;
import gift.domain.user.entity.Role;
import gift.domain.user.entity.User;
import gift.domain.wishlist.entity.WishItem;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WishlistJpaRepositoryTest {
    
    @Autowired
    private WishlistJpaRepository wishlistJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;
    

    @Test
    @DisplayName("위시리스트 저장 테스트")
    void save() {
        // given
        User user = new User(null, "testUser", "test@test.com", "test123", Role.USER);
        Product product = new Product(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");

        User savedUser = userJpaRepository.save(user);
        Product savedProduct = productJpaRepository.save(product);

        WishItem expected = new WishItem(null, savedUser, savedProduct);

        // when
        WishItem actual = wishlistJpaRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getUserId()).isEqualTo(savedUser.getId()),
            () -> assertThat(actual.getProductId()).isEqualTo(savedProduct.getId())
        );
    }

    @Test
    @DisplayName("위시리스트 사용자 ID로 조회 테스트")
    void findAllByUser_id() {
        // given
        User user = new User(null, "testUser", "test@test.com", "test123", Role.USER);
        Product product = new Product(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");

        User savedUser = userJpaRepository.save(user);
        Product savedProduct = productJpaRepository.save(product);

        WishItem wishItem = new WishItem(null, savedUser, savedProduct);
        WishItem expected = wishlistJpaRepository.save(wishItem);

        // when
        List<WishItem> allByUserId = wishlistJpaRepository.findAllByUser_id(savedUser.getId());

        // then
        assertAll(
            () -> assertThat(allByUserId.size()).isEqualTo(1),
            () -> assertThat(allByUserId.get(0).getId()).isEqualTo(expected.getId()),
            () -> assertThat(allByUserId.get(0).getUserId()).isEqualTo(expected.getUserId()),
            () -> assertThat(allByUserId.get(0).getProductId()).isEqualTo(expected.getProductId())
        );
    }

    @Test
    @DisplayName("위시리스트 ID로 조회 테스트")
    void findById() {
        // given
        User user = new User(null, "testUser", "test@test.com", "test123", Role.USER);
        Product product = new Product(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");

        User savedUser = userJpaRepository.save(user);
        Product savedProduct = productJpaRepository.save(product);

        WishItem wishItem = new WishItem(null, savedUser, savedProduct);
        WishItem expected = wishlistJpaRepository.save(wishItem);

        // when
        WishItem actual = wishlistJpaRepository.findById(expected.getId()).get();

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getUserId()).isEqualTo(savedUser.getId()),
            () -> assertThat(actual.getProductId()).isEqualTo(savedProduct.getId())
        );
    }

    @Test
    @DisplayName("위시리스트 삭제 테스트")
    void delete() {
        // given
        User user = new User(null, "testUser", "test@test.com", "test123", Role.USER);
        Product product = new Product(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");

        User savedUser = userJpaRepository.save(user);
        Product savedProduct = productJpaRepository.save(product);

        WishItem wishItem = new WishItem(null, savedUser, savedProduct);
        WishItem saved = wishlistJpaRepository.save(wishItem);

        // when
        wishlistJpaRepository.delete(saved);

        // then
        Optional<WishItem> deletedProduct = wishlistJpaRepository.findById(saved.getId());
        assertThat(deletedProduct).isEmpty();
    }
}