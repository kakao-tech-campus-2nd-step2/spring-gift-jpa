package gift.api.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        Product expected = new Product("americano", 4500, "/image/americano");
        Product actual = productRepository.save(expected);

        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual).isEqualTo(expected)
        );
    }

    @Test
    void deleteById() {
        Long id = 1L;
        Product product = new Product(id, "name", 1000, "url");
        productRepository.save(product);
        productRepository.deleteById(id);

        assertThat(productRepository.findById(id)).isEmpty();
    }
}