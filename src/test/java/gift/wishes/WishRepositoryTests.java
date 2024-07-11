package gift.wishes;

import gift.product.infrastructure.persistence.JpaProductRepository;
import gift.product.infrastructure.persistence.ProductEntity;
import gift.user.infrastructure.persistence.JpaUserRepository;
import gift.user.infrastructure.persistence.UserEntity;
import gift.wishes.infrastructure.persistence.JpaWishRepository;
import gift.wishes.infrastructure.persistence.WishEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WishRepositoryTests {
    @Autowired
    private JpaWishRepository jpaWishRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private JpaProductRepository jpaProductRepository;

    @Test
    public void saveWish() {
        UserEntity user = new UserEntity("test");
        user = jpaUserRepository.save(user);
        ProductEntity product = new ProductEntity("test", 100, "test");
        product = jpaProductRepository.save(product);

        WishEntity wish = new WishEntity(user, product);

        wish = jpaWishRepository.save(wish);
        assertThat(jpaWishRepository.existsByUser_IdAndProduct_Id(user.getId(), product.getId())).isTrue();
        assertThat(jpaWishRepository.findById(wish.getId()).get()).isEqualTo(wish);
    }

    @Test
    public void removeWish() {
        UserEntity user = new UserEntity("test");
        user = jpaUserRepository.save(user);
        ProductEntity product = new ProductEntity("test", 100, "test");
        product = jpaProductRepository.save(product);

        WishEntity wish = new WishEntity(user, product);
        wish = jpaWishRepository.save(wish);
        jpaWishRepository.deleteByUser_IdAndProduct_Id(user.getId(), wish.getId());
        assertThat(jpaWishRepository.existsByUser_IdAndProduct_Id(wish.getId(), wish.getId())).isFalse();
    }

    @Test
    public void existsByUserIdAndProductId() {
        UserEntity user = new UserEntity("test");
        user = jpaUserRepository.save(user);
        ProductEntity product = new ProductEntity("test", 100, "test");
        product = jpaProductRepository.save(product);
        WishEntity wish = new WishEntity(user, product);

        wish = jpaWishRepository.save(wish);
        assertThat(jpaWishRepository.existsByUser_IdAndProduct_Id(wish.getId(), wish.getId())).isTrue();
    }

    @Test
    public void findAllByUserId() {
        UserEntity user = new UserEntity("test");
        user = jpaUserRepository.save(user);
        ProductEntity product = new ProductEntity("test", 100, "test");
        product = jpaProductRepository.save(product);
        WishEntity wish = new WishEntity(user, product);
        jpaWishRepository.save(wish);

        assertThat(jpaWishRepository.findAllByUser(user).size()).isEqualTo(1);
    }
}
