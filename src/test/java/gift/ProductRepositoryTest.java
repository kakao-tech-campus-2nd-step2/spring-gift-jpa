package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.product.Product;
import gift.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTest {

    private final ProductRepository productRepository;

    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Test
    void save() {
        Product expected = new Product(1L, "아메리카노", 1000, "no image");

        Product actual = productRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void deleteById() {
        Long id = 1L;
        Product expected = new Product(1L, "아메리카노", 1000, "no image");
        productRepository.save(expected);

        productRepository.deleteById(id);

        assertThat(productRepository.findById(id)).isEmpty();
    }
}
