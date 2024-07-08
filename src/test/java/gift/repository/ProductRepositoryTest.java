package gift.repository;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import gift.entity.Product;
import jakarta.transaction.Transactional;

@DataJpaTest
public class ProductRepositoryTest {
    
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    @Test
    void save() {
        Product expected = new Product("testName", 1, "testUrl");
        Product actual = productRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Transactional
    @Test
    void findById() {

        Product expected = new Product("testName", 1, "testUrl");
        expected.setId(1L);
        productRepository.save(expected);

        Optional<Product> product = productRepository.findById(expected.getId());

        assertAll(
            () -> assertThat(product).isPresent(),
            () -> assertThat(product.get().getId()).isEqualTo(expected.getId())
        );
    }

    @Transactional
    @Test
    void update() {

        Product expected = new Product("testName", 1, "testUrl");
        expected.setId(1L);
        productRepository.save(expected);

        String updateName = "update name";

        Product product = productRepository.findById(expected.getId()).orElseThrow();
        product.setName(updateName);
        productRepository.save(product);

        Optional<Product> updatedProduct = productRepository.findById(expected.getId());

        assertAll(
            () -> assertThat(updatedProduct).isPresent(),
            () -> assertThat(updatedProduct.get().getName()).isEqualTo(updateName)
        );
    }

    @Transactional
    @Test
    void delete(){

        Product expected = new Product("testName", 1, "testUrl");
        expected.setId(1L);
        productRepository.save(expected);
        
        long productId = 1L;
        productRepository.deleteById(productId);

        Optional<Product> product = productRepository.findById(productId);
        assertThat(product).isNotPresent();
    }

}
