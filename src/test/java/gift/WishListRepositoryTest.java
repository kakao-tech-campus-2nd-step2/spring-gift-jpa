package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import gift.model.Name;
import gift.model.Product;
import gift.model.User;
import gift.model.WishList;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishListRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        User user = new User(null, "test@example.com", "password");
        userRepository.save(user);

        Product product = new Product(null, new Name("Test Product"), 100, "http://example.com/image.png");
        productRepository.save(product);

        WishList wishList = new WishList(null, user, Arrays.asList(product));
        WishList savedWishList = wishListRepository.save(wishList);

        assertAll(
            () -> assertThat(savedWishList.getId()).isNotNull(),
            () -> assertThat(savedWishList.getUser().getEmail()).isEqualTo(user.getEmail()),
            () -> assertThat(savedWishList.getProducts()).contains(product)
        );
    }

    @Test
    void findByUser() {
        User user = new User(null, "test@example.com", "password");
        userRepository.save(user);

        WishList wishList = new WishList(null, user, new ArrayList<>());
        wishListRepository.save(wishList);

        Optional<WishList> actual = wishListRepository.findByUser(user);
        assertTrue(actual.isPresent());
        assertThat(actual.get().getUser().getEmail()).isEqualTo(user.getEmail());
    }
}