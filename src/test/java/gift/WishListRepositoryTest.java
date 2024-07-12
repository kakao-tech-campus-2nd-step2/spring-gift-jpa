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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

        Product product = new Product(null, new Name("TestProduct"), 100, "http://example.com/image.png");
        productRepository.save(product);

        WishList wishList = new WishList(null, user, product);
        WishList savedWishList = wishListRepository.save(wishList);

        assertAll(
            () -> assertThat(savedWishList.getId()).isNotNull(),
            () -> assertThat(savedWishList.getUser().getEmail()).isEqualTo(user.getEmail()),
            () -> assertThat(savedWishList.getProduct()).isEqualTo(product)
        );
    }

    @Test
    void findByUser() {
        User user = new User(null, "test@example.com", "password");
        userRepository.save(user);

        Product product = new Product(null, new Name("TestProduct"), 100, "http://example.com/image.png");
        productRepository.save(product);

        WishList wishList = new WishList(null, user, product);
        wishListRepository.save(wishList);

        List<WishList> wishLists = wishListRepository.findByUser(user);
        assertThat(wishLists).hasSize(1);
        assertThat(wishLists.get(0).getUser().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void findByUserWithPagination() {
        User user = new User(null, "test@example.com", "password");
        userRepository.save(user);

        for (int i = 1; i <= 20; i++) {
            Product product = new Product(null, new Name("TestProduct" + i), 100 + i, "http://example.com/image" + i + ".png");
            productRepository.save(product);
            WishList wishList = new WishList(null, user, product);
            wishListRepository.save(wishList);
        }

        Pageable pageable = PageRequest.of(0, 10);
        Page<WishList> wishListPage = wishListRepository.findByUser(user, pageable);

        assertAll(
            () -> assertThat(wishListPage.getTotalElements()).isEqualTo(20),
            () -> assertThat(wishListPage.getContent()).hasSize(10),
            () -> assertThat(wishListPage.getTotalPages()).isEqualTo(2)
        );
    }
}