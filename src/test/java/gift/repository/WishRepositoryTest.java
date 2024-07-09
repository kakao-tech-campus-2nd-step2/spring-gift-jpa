package gift.repository;

import gift.model.Product;
import gift.model.User;
import gift.model.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WishRepositoryTest {
    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        Wish expected = new Wish(2L, 3L);

        Wish actual = wishRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getUserId()).isEqualTo(expected.getUserId()),
                () -> assertThat(actual.getProductId()).isEqualTo(expected.getProductId())
        );
    }

    @Test
    void findProductsByUserId() {
        User user = new User("test@test.com", "test", "user");
        user = userRepository.save(user);

        Product product1 = new Product("cookie", 2000L, "www.cookie.com");
        Product product2 = new Product("pie", 5000L, "www.pie.com");

        product1 = productRepository.save(product1);
        product2 = productRepository.save(product2);

        Wish wish1 = new Wish(user.getId(), product1.getId());
        Wish wish2 = new Wish(user.getId(), product2.getId());

        wishRepository.saveAll(Arrays.asList(wish1, wish2));

        // When
        List<Long> productIds = wishRepository.findProductIdsByUserId(user.getId());

        // Then
        assertThat(productIds).hasSize(2);
        assertThat(productIds).contains(product1.getId(), product2.getId());
    }

    @Test
    void deleteByUserIdAndProductId() {
        wishRepository.deleteByUserIdAndProductId(2L, 1L);
        Optional<Wish> deletedWish = wishRepository.findById(1L);

        assertThat(deletedWish).isEmpty();
    }
}