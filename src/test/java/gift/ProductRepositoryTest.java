package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import gift.model.Name;
import gift.model.Product;
import gift.repository.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        Product expected = new Product(null, new Name("Test Product"), 100, "http://example.com/image.png");
        Product actual = productRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName().getName()).isEqualTo(expected.getName().getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    void findById() {
        Product expected = new Product(null, new Name("Test Product"), 100, "http://example.com/image.png");
        productRepository.save(expected);
        Optional<Product> actual = productRepository.findById(expected.getId());
        assertTrue(actual.isPresent());
        assertThat(actual.get().getName().getName()).isEqualTo(expected.getName().getName());
    }
}