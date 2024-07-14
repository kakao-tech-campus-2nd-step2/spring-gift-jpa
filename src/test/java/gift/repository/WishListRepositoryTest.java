package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.model.product.Product;
import gift.model.user.User;
import gift.model.wishlist.WishList;
import gift.repository.product.ProductRepository;
import gift.repository.user.UserRepository;
import gift.repository.wish.WishListRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishListRepositoryTest {

    private WishListRepository wishListRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    @Autowired
    public WishListRepositoryTest(WishListRepository wishListRepository,
        ProductRepository productRepository, UserRepository userRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @DisplayName("위시리스트 정보 저장 테스트")
    @Test
    void save() {
        // given
        Product product = createProduct("americano", 4500, "americano");
        productRepository.save(product);
        User user = createUser("yj", "yj@google.com", "password", "BE");
        userRepository.save(user);
        WishList wish = new WishList(user, product, 2);
        // when

        WishList savedWish = wishListRepository.save(wish);
        // then
        Assertions.assertAll(
            () -> assertThat(savedWish.getId()).isNotNull(),
            () -> assertThat(savedWish.getQuantity()).isEqualTo(wish.getQuantity())
        );
    }

    @DisplayName("id에 따른 위시 리스트 찾기 테스트")
    @Test
    void findById() {
        // given
        Product product = createProduct("americano", 4500, "americano");
        productRepository.save(product);
        User user = createUser("yj", "yj@google.com", "password", "BE");
        userRepository.save(user);
        WishList wish = new WishList(user, product, 2);
        wishListRepository.save(wish);
        Long id = wish.getId();

        // when
        Optional<WishList> findWish = wishListRepository.findById(id);
        // then
        assertThat(findWish).isNotNull();
    }

    @DisplayName("위시 리스트 삭제 기능 테스트")
    @Test
    void deleteById() {
        // given
        Product product = createProduct("americano", 4500, "americano");
        productRepository.save(product);
        User user = createUser("yj", "yj@google.com", "password", "BE");
        userRepository.save(user);
        WishList wish = new WishList(user, product, 2);
        wishListRepository.save(wish);
        Long deleteId = wish.getId();
        // when
        wishListRepository.deleteById(deleteId);
        List<WishList> savedWish = wishListRepository.findAll();
        // then
        assertThat(savedWish.size()).isEqualTo(0);
    }

    private Product createProduct(String name, int price, String url) {
        return new Product(name, price, url);
    }

    private User createUser(String name, String email, String password, String Role) {
        return new User(name, email, password, Role);
    }

    private WishList createWish(User user, Product product, int quantity) {
        return new WishList(user, product, quantity);
    }
}