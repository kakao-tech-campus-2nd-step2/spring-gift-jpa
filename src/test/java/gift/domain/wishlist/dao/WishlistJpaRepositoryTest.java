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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
            () -> assertThat(actual.getUser()).isEqualTo(savedUser),
            () -> assertThat(actual.getProduct()).isEqualTo(savedProduct)
        );
    }

    @Test
    @DisplayName("위시리스트 사용자 ID로 조회 테스트")
    void findAllByUserId() {
        // given
        User user = new User(null, "testUser", "test@test.com", "test123", Role.USER);
        Product product1 = new Product(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");
        Product product2 = new Product(null, "아이스 카페 아메리카노 T", 4500, "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg");


        User savedUser = userJpaRepository.save(user);
        Product savedProduct1 = productJpaRepository.save(product1);
        Product savedProduct2 = productJpaRepository.save(product2);

        WishItem wishItem1 = new WishItem(null, savedUser, savedProduct1);
        WishItem wishItem2 = new WishItem(null, savedUser, savedProduct2);
        WishItem savedWishItem1 = wishlistJpaRepository.save(wishItem1);
        WishItem savedWishItem2 = wishlistJpaRepository.save(wishItem2);

        // when
        List<WishItem> wishlist = wishlistJpaRepository.findAllByUserId(savedUser.getId(), PageRequest.of(0, 5)).getContent();

        // then
        assertAll(
            () -> assertThat(wishlist.size()).isEqualTo(2),
            () -> assertThat(wishlist.get(0).getId()).isNotNull(),
            () -> assertThat(wishlist.get(0).getUser()).isEqualTo(savedWishItem1.getUser()),
            () -> assertThat(wishlist.get(0).getProduct()).isEqualTo(savedWishItem1.getProduct()),
            () -> assertThat(wishlist.get(1).getId()).isNotNull(),
            () -> assertThat(wishlist.get(1).getUser()).isEqualTo(savedWishItem2.getUser()),
            () -> assertThat(wishlist.get(1).getProduct()).isEqualTo(savedWishItem2.getProduct())
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
            () -> assertThat(actual.getUser()).isEqualTo(savedUser),
            () -> assertThat(actual.getProduct()).isEqualTo(savedProduct)
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

    @Test
    @DisplayName("위시리스트 사용자 ID로 삭제 테스트")
    void deleteAllByUserId() {
        // given
        User user = new User(null, "testUser", "test@test.com", "test123", Role.USER);
        Product product1 = new Product(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");
        Product product2 = new Product(null, "탕종종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");

        User savedUser = userJpaRepository.save(user);
        Product savedProduct1 = productJpaRepository.save(product1);
        Product savedProduct2 = productJpaRepository.save(product2);

        WishItem wishItem1 = new WishItem(null, savedUser, savedProduct1);
        WishItem saved1 = wishlistJpaRepository.save(wishItem1);
        WishItem wishItem2 = new WishItem(null, savedUser, savedProduct2);
        WishItem saved2 = wishlistJpaRepository.save(wishItem2);

        // when
        wishlistJpaRepository.deleteAllByUserId(savedUser.getId());

        // then
        List<WishItem> deletedProduct = wishlistJpaRepository.findAllByUserId(savedUser.getId(), PageRequest.of(0, 5)).getContent();
        assertThat(deletedProduct).isEmpty();
    }

    @Test
    @DisplayName("위시리스트 상품 ID로 삭제 테스트")
    void deleteAllByProductId() {
        // given
        User user = new User(null, "testUser", "test@test.com", "test123", Role.USER);
        Product product1 = new Product(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");
        Product product2 = new Product(null, "탕종종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");

        User savedUser = userJpaRepository.save(user);
        Product savedProduct1 = productJpaRepository.save(product1);
        Product savedProduct2 = productJpaRepository.save(product2);

        WishItem wishItem1 = new WishItem(null, savedUser, savedProduct1);
        WishItem saved1 = wishlistJpaRepository.save(wishItem1);
        WishItem wishItem2 = new WishItem(null, savedUser, savedProduct2);
        WishItem saved2 = wishlistJpaRepository.save(wishItem2);

        // when
        wishlistJpaRepository.deleteAllByProductId(savedProduct2.getId());

        // then
        List<WishItem> deletedProduct = wishlistJpaRepository.findAllByUserId(savedUser.getId(), PageRequest.of(0, 5)).getContent();
        assertThat(deletedProduct.size()).isEqualTo(1);
    }
}