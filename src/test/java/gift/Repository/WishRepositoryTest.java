package gift.Repository;

import gift.Entity.WishEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Test
    public void testSaveWish() {
        WishEntity wish = new WishEntity(null, 1L, 1L, "Product Name");

        WishEntity savedWish = wishRepository.save(wish);

        assertThat(savedWish).isNotNull();
        assertThat(savedWish.getId()).isNotNull();
        assertThat(savedWish.getUserId()).isEqualTo(1L);
        assertThat(savedWish.getProductId()).isEqualTo(1L);
        assertThat(savedWish.getProductName()).isEqualTo("Product Name");
    }

    @Test
    public void testFindById() {
        WishEntity wish = new WishEntity(null, 2L, 2L, "Another Product");
        WishEntity savedWish = wishRepository.save(wish);

        Optional<WishEntity> foundWish = wishRepository.findById(savedWish.getId());

        assertThat(foundWish).isPresent();
        assertThat(foundWish.get().getUserId()).isEqualTo(2L);
        assertThat(foundWish.get().getProductId()).isEqualTo(2L);
        assertThat(foundWish.get().getProductName()).isEqualTo("Another Product");
    }
}
