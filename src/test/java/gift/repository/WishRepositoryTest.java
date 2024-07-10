package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.Product;
import gift.entity.User;
import gift.entity.Wish;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class WishRepositoryTest {

    @Autowired
    WishRepository wishRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    EntityManager entityManager;

    private User user;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        user = new User("user@test.com", "password");
        userRepository.save(user);

        product1 = new Product("Product 1", 10000, "image1.jpg");
        product2 = new Product("Product 2", 2000, "image2.jpg");
        productRepository.save(product1);
        productRepository.save(product2);
    }

    @Test
    @DisplayName("유저 아이디 기반 위시리스트 반환 테스트")
    void findByUserId() {
        Wish wish1 = new Wish(user, product1, 10);
        Wish wish2 = new Wish(user, product2, 15);
        wishRepository.save(wish1);
        wishRepository.save(wish2);
        List<Wish> wishes = wishRepository.findByUserId(user.getId());

        assertThat(wishes).hasSize(2);
        assertThat(wishes.getFirst().getProduct().getName()).isEqualTo(wish1.getProduct().getName());
    }

    @Test
    @DisplayName("유저 아이디와 위시 아이디 기반 위시 객체 반환 테스트")
    void findByUserIdAndId() {
        Wish wish = new Wish(user, product1, 10);
        Wish actual = wishRepository.save(wish);
        Wish expected = wishRepository.findByUserIdAndId(user.getId(), actual.getId());

        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getProduct().getId()).isEqualTo(expected.getProduct().getId());
    }

    @Test
    @DisplayName("위시 리스트 특정 객체 수량 변경 테스트")
    void updateWishNumber() {
        Wish wish = new Wish(user, product1, 10);
        Wish savedWish = wishRepository.save(wish);
        wishRepository.updateWishNumber(user.getId(), savedWish.getId(), 30);
        entityManager.flush();
        entityManager.clear();

        Wish updatedWish = wishRepository.findByUserIdAndId(user.getId(), savedWish.getId());

        assertThat(updatedWish).isNotNull();
        assertThat(updatedWish.getNumber()).isEqualTo(30);
    }
}
