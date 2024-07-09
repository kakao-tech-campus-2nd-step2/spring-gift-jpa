package gift.wishes;

import gift.product.infrastructure.persistence.JpaProductRepository;
import gift.product.infrastructure.persistence.ProductEntity;
import gift.wishes.infrastructure.persistence.JpaWishRepository;
import gift.wishes.infrastructure.persistence.WishEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WishRepositoryTests {
    @Autowired
    private JpaWishRepository jpaWishRepository;

    @Autowired
    private JpaProductRepository jpaProductRepository;

    @Test
    public void saveWish() {
        WishEntity wish = new WishEntity(0L, 1L, 1L);

        wish = jpaWishRepository.save(wish);
        assertThat(jpaWishRepository.existsByUserIdAndProductId(wish.getUserId(), wish.getProductId())).isTrue();
        assertThat(jpaWishRepository.findById(wish.getId()).get()).isEqualTo(wish);
    }

    @Test
    public void removeWish() {
        WishEntity wish = new WishEntity(0L, 1L, 1L);

        wish = jpaWishRepository.save(wish);
        jpaWishRepository.deleteByUserIdAndProductId(wish.getUserId(), wish.getProductId());
        assertThat(jpaWishRepository.existsByUserIdAndProductId(wish.getUserId(), wish.getProductId())).isFalse();
    }

    @Test
    public void existsByUserIdAndProductId() {
        WishEntity wish = new WishEntity(0L, 1L, 1L);

        wish = jpaWishRepository.save(wish);
        assertThat(jpaWishRepository.existsByUserIdAndProductId(wish.getUserId(), wish.getProductId())).isTrue();
    }

    @Test
    public void findAllByUserId() {
        ProductEntity product = new ProductEntity(0L, "test", 100, "test");
        product = jpaProductRepository.save(product);
        WishEntity wish = new WishEntity(0L, 1L, product.getId());

        wish = jpaWishRepository.save(wish);
        assertThat(jpaWishRepository.findAllByUserId(wish.getUserId())).isNotEmpty();
    }
}
