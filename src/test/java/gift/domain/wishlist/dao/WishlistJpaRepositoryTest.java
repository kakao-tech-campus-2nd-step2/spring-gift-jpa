package gift.domain.wishlist.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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
import org.springframework.boot.test.mock.mockito.MockBean;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WishlistJpaRepositoryTest {
    
    @Autowired
    private WishlistJpaRepository wishlistJpaRepository;

    @MockBean
    private UserJpaRepository userJpaRepository;

    @MockBean
    private ProductJpaRepository productJpaRepository;
    

    @Test
    @DisplayName("위시리스트 저장 테스트")
    void save() {
        // given
        User user = new User(null, "testUser", "test@test.com", "test123", Role.USER);
        Product product = new Product(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");

        given(userJpaRepository.save(any(User.class))).willReturn(user);
        given(productJpaRepository.save(any(Product.class))).willReturn(product);

        WishItem expected = new WishItem(null, user, product);

        // when
        WishItem actual = wishlistJpaRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getUser()).isEqualTo(user),
            () -> assertThat(actual.getProduct()).isEqualTo(product)
        );
    }

    @Test
    @DisplayName("위시리스트 사용자 ID로 조회 테스트")
    void findAllByUserId() {
        // given
        User user = new User(null, "testUser", "test@test.com", "test123", Role.USER);
        Product product = new Product(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");

        given(userJpaRepository.save(any(User.class))).willReturn(user);
        given(productJpaRepository.save(any(Product.class))).willReturn(product);

        WishItem wishItem = new WishItem(null, user, product);
        WishItem expected = wishlistJpaRepository.save(wishItem);

        user.setId(wishItem.getUserId());

        // when
        List<WishItem> allByUserId = wishlistJpaRepository.findAllByUserId(user.getId());

        // then
        assertAll(
            () -> assertThat(allByUserId.size()).isEqualTo(1),
            () -> assertThat(allByUserId.get(0).getId()).isNotNull(),
            () -> assertThat(allByUserId.get(0).getUser()).isEqualTo(expected.getUser()),
            () -> assertThat(allByUserId.get(0).getProduct()).isEqualTo(expected.getProduct())
        );
    }

    @Test
    @DisplayName("위시리스트 ID로 조회 테스트")
    void findById() {
        // given
        User user = new User(null, "testUser", "test@test.com", "test123", Role.USER);
        Product product = new Product(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");

        given(userJpaRepository.save(any(User.class))).willReturn(user);
        given(productJpaRepository.save(any(Product.class))).willReturn(product);

        WishItem wishItem = new WishItem(null, user, product);
        WishItem expected = wishlistJpaRepository.save(wishItem);

        // when
        WishItem actual = wishlistJpaRepository.findById(expected.getId()).get();

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getUser()).isEqualTo(user),
            () -> assertThat(actual.getProduct()).isEqualTo(product)
        );
    }

    @Test
    @DisplayName("위시리스트 삭제 테스트")
    void delete() {
        // given
        User user = new User(null, "testUser", "test@test.com", "test123", Role.USER);
        Product product = new Product(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");

        given(userJpaRepository.save(any(User.class))).willReturn(user);
        given(productJpaRepository.save(any(Product.class))).willReturn(product);

        WishItem wishItem = new WishItem(null, user, product);
        WishItem saved = wishlistJpaRepository.save(wishItem);

        // when
        wishlistJpaRepository.delete(saved);

        // then
        Optional<WishItem> deletedProduct = wishlistJpaRepository.findById(saved.getId());
        assertThat(deletedProduct).isEmpty();
    }
}