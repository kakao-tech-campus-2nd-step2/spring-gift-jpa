package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.Name;
import gift.model.Product;
import gift.model.User;
import gift.model.WishList;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishListRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        User savedUser = userRepository.save(user);

        Product product = new Product(null, new Name("테스트상품"), 1000, "test.jpg");
        Product savedProduct = productRepository.save(product);

        WishList wishList = new WishList();
        wishList.setUser(savedUser);
        wishList.setProducts(List.of(savedProduct));
        WishList savedWishList = wishListRepository.save(wishList);

        assertAll(
            () -> assertThat(savedWishList.getId()).isNotNull(),
            () -> assertThat(savedWishList.getUser().getEmail()).isEqualTo(savedUser.getEmail()),
            () -> assertThat(savedWishList.getProducts()).contains(savedProduct)
        );
    }

    @Test
    void findByUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        User savedUser = userRepository.save(user);

        Product product = new Product(null, new Name("테스트상품"), 1000, "test.jpg");
        Product savedProduct = productRepository.save(product);

        WishList wishList = new WishList();
        wishList.setUser(savedUser);
        wishList.setProducts(List.of(savedProduct));
        wishListRepository.save(wishList);

        WishList foundWishList = wishListRepository.findByUser(savedUser);
        assertAll(
            () -> assertThat(foundWishList).isNotNull(),
            () -> assertThat(foundWishList.getUser().getEmail()).isEqualTo(savedUser.getEmail()),
            () -> assertThat(foundWishList.getProducts()).contains(savedProduct)
        );
    }
}