package gift;

import gift.domain.model.entity.Product;
import gift.domain.model.entity.User;
import gift.domain.model.entity.Wish;
import gift.domain.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WishTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WishRepository wishRepository;

    private User user;
    private Product product;

    @BeforeEach
    public void setUp() {
        user = new User("test@example.com", "password123");
        product = new Product("Test Product", 1000L, "http://example.com/image.jpg");
        entityManager.persist(user);
        entityManager.persist(product);
        entityManager.flush();
    }

    @Test
    public void saveWishTest() {
        // given
        Wish wish = new Wish(user, product);

        // when
        Wish savedWish = wishRepository.save(wish);

        // then
        assertThat(savedWish).isNotNull();
        assertThat(savedWish.getId()).isNotNull();
        assertThat(savedWish.getUser()).isEqualTo(user);
        assertThat(savedWish.getProduct()).isEqualTo(product);
        assertThat(savedWish.getCount()).isEqualTo(1);
    }

    @Test
    public void findByIdTest() {
        // given
        Wish wish = new Wish(user, product);
        Wish savedWish = wishRepository.save(wish);

        // when
        Wish found = wishRepository.findById(savedWish.getId()).orElse(null);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getUser().getId()).isEqualTo(user.getId());
        assertThat(found.getProduct().getId()).isEqualTo(product.getId());
        assertThat(found.getCount()).isEqualTo(1);
    }

    @Test
    public void updateWishTest() {
        // given
        Wish wish = new Wish(user, product);
        Wish savedWish = wishRepository.save(wish);

        // when
        savedWish.setCount(2);
        Wish updatedWish = wishRepository.save(savedWish);

        // then
        assertThat(updatedWish).isNotNull();
        assertThat(updatedWish.getCount()).isEqualTo(2);
    }

    @Test
    public void deleteWishTest() {
        // given
        Wish wish = new Wish(user, product);
        Wish savedWish = wishRepository.save(wish);

        // when
        wishRepository.delete(savedWish);

        // then
        assertThat(wishRepository.findById(savedWish.getId())).isEmpty();
    }

    @Test
    public void findByUserEmailTest() {
        // given
        Wish wish = new Wish(user, product);
        wishRepository.save(wish);

        // when
        List<Wish> found = wishRepository.findByUserEmail(user.getEmail());

        // then
        assertThat(found).isNotNull();
        assertThat(found).isNotEmpty();
        assertThat(found.size()).isEqualTo(1);

        Wish foundWish = found.getFirst();
        assertThat(foundWish.getUser().getId()).isEqualTo(user.getId());
        assertThat(foundWish.getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    public void findByUserEmailAndProductIdTest() {
        // given
        Wish wish = new Wish(user, product);
        wishRepository.save(wish);

        // when
        Optional<Wish> foundOptional = wishRepository.findByUserEmailAndProductId(user.getEmail(),
            product.getId());

        // then
        assertThat(foundOptional).isPresent();

        Wish found = foundOptional.get();
        assertThat(found.getUser()).isEqualTo(user);
        assertThat(found.getProduct()).isEqualTo(product);
        assertThat(found.getCount()).isEqualTo(1);
    }

    @Test
    public void existsByUserEmailAndProductIdTest() {
        // given
        Wish wish = new Wish(user, product);
        wishRepository.save(wish);

        // when
        boolean exists = wishRepository.existsByUserEmailAndProductId(user.getEmail(),
            product.getId());
        boolean notExists = wishRepository.existsByUserEmailAndProductId("nonexistent@email.com",
            product.getId());

        // then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    public void deleteByUserEmailAndProductIdTest() {
        // given
        Wish wish = new Wish(user, product);
        wishRepository.save(wish);

        assertThat(wishRepository.existsByUserEmailAndProductId(user.getEmail(),
            product.getId())).isTrue();

        // when
        wishRepository.deleteByUserEmailAndProductId(user.getEmail(), product.getId());

        // then
        assertThat(wishRepository.existsByUserEmailAndProductId(user.getEmail(),
            product.getId())).isFalse();

        List<Wish> remainingWishes = wishRepository.findByUserEmail(user.getEmail());
        assertThat(remainingWishes).isEmpty();
    }
}