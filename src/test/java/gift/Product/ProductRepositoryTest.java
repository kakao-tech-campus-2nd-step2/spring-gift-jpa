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

    @Test
    void save(){
        Product expected = new Product("초코라떼", 3500, "example2.com");
        Product actual = productRepository.save(expected);
        assertThat(actual.getId()).isEqualTo(expected.getId());
    }

    @Test
    void delete(){
        Product expected = new Product("그린티라떼", 3500, "example3.com");
        productRepository.save(expected);
        productRepository.deleteById(expected.getId());
        Optional<Product> actual = productRepository.findById(expected.getId());
        assertThat(actual).isNotPresent();
    }

}
