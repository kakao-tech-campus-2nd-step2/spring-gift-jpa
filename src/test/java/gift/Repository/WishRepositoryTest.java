package gift.Repository;

import gift.Entity.ProductEntity;
import gift.Entity.UserEntity;
import gift.Entity.WishEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveWish() {
        UserEntity user = new UserEntity("user@example.com", "password");
        ProductEntity product = new ProductEntity(null, "Product Name", 100, "image-url");

        user = entityManager.persistAndFlush(user);
        product = entityManager.persistAndFlush(product);

        WishEntity wish = new WishEntity(null, user, product, "Product Name");

        WishEntity savedWish = wishRepository.save(wish);

        assertThat(savedWish).isNotNull();
        assertThat(savedWish.getId()).isNotNull();
        assertThat(savedWish.getUser().getId()).isEqualTo(user.getId());
        assertThat(savedWish.getProduct().getId()).isEqualTo(product.getId());
        assertThat(savedWish.getProductName()).isEqualTo("Product Name");
    }

    @Test
    public void testFindById() {
        UserEntity user = new UserEntity("user2@example.com", "password2");
        ProductEntity product = new ProductEntity(null, "Another Product", 200, "image-url2");

        user = entityManager.persistAndFlush(user);
        product = entityManager.persistAndFlush(product);

        WishEntity wish = new WishEntity(null, user, product, "Another Product");
        WishEntity savedWish = wishRepository.save(wish);

        Optional<WishEntity> foundWish = wishRepository.findById(savedWish.getId());

        assertThat(foundWish).isPresent();
        assertThat(foundWish.get().getUser().getId()).isEqualTo(user.getId());
        assertThat(foundWish.get().getProduct().getId()).isEqualTo(product.getId());
        assertThat(foundWish.get().getProductName()).isEqualTo("Another Product");
    }
}
