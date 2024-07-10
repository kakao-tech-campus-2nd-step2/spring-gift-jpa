package gift.Product;

import static org.assertj.core.api.Assertions.assertThat;

import gift.model.Product;
import gift.repository.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void testFindById() {
        Product expected = new Product("카푸치노", 3000, "example.com");
        productRepository.save(expected);
        Optional<Product> actual = productRepository.findById(expected.getId());
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getName()).isEqualTo("카푸치노");
    }

}
